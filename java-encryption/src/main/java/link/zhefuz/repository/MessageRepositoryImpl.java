package link.zhefuz.repository;

import link.zhefuz.grpc.MessageOuterClass;

import java.sql.*;
import java.util.Properties;

public class MessageRepositoryImpl implements MessageRepository {
	public final static MessageRepositoryImpl INSTANCE = new MessageRepositoryImpl();
	private Connection connection;

	private MessageRepositoryImpl() {
		try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void connect() throws SQLException {
		String url = "jdbc:postgresql://postgres:5432/postgres";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "pA1zsWO10FDsBd");

		connection = DriverManager.getConnection(url, props);
		Statement stmt = connection.createStatement();
		String sql = "CREATE TABLE messages (" +
				"id SERIAL PRIMARY KEY," +
				"encryptMethod TEXT," +
				"cipherText TEXT" +
				")";
		stmt.executeUpdate(sql);

	}

	public static MessageRepositoryImpl getInstance() {

		return INSTANCE;
	}

	@Override
	public MessageOuterClass.Message saveMessage(MessageOuterClass.Message message) {
		// TODO: finish JDBC implementation

		String sql = "INSERT INTO messages ( encryptMethod, cipherText) VALUES (?, ?)";


		try {
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, message.getEncryptionMethod());
			pstmt.setString(2, message.getCipherText());
			pstmt.executeUpdate();

			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			int id = 0;
			if (generatedKeys.next()) {
				id = (int) generatedKeys.getLong(1); // 获取生成的 ID
				System.out.println("Generated ID: " + id);
			}
			MessageOuterClass.Message msg = message.toBuilder()
					.setId(id)
					.setEncryptionMethod(message.getEncryptionMethod())
					.setCipherText(message.getCipherText())
					.setContent(message.getContent())
					.build();
			return msg;

		} catch (Exception e) {
			MessageOuterClass.Message msg = MessageOuterClass.Message.newBuilder()
					.setContent("Error in saving the request message")
					.setCipherText("Error in saving the request message")
					.setEncryptionMethod("Error in saving the request message")
					.setId(0)
					.build();

			return msg;
		}

	}

	@Override
	public MessageOuterClass.Message getMessage(int id) {

		String sql = "SELECT * FROM messages WHERE id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String encryptMethod = rs.getString("encryptmethod");
				String cipherText = rs.getString("cipherText"); // 假设 ID 列的名称是 "id"
				MessageOuterClass.Message msg = MessageOuterClass.Message.newBuilder()
						.setId(id)
						.setEncryptionMethod(encryptMethod)
						.setCipherText(cipherText)
						.build();
				rs.close();
				return msg;
			} else {
				MessageOuterClass.Message msg = MessageOuterClass.Message.newBuilder()
						.setContent("Error in saving the request message")
						.setCipherText("Error in saving the request message")
						.setEncryptionMethod("Error in saving the request message")
						.setId(0)
						.build();
				rs.close();
				return msg;
			}
		} catch (Exception e) {
			MessageOuterClass.Message msg = MessageOuterClass.Message.newBuilder()
					.setContent("Error in saving the request message")
					.setCipherText("Error in saving the request message")
					.setEncryptionMethod("Error in saving the request message")
					.setId(0)
					.build();

			return msg;
		}

	}
}
