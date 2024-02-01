package link.zhefuz.grpc;

import io.grpc.stub.StreamObserver;
import link.zhefuz.encryption.Encryption;
import link.zhefuz.repository.MessageRepository;
import link.zhefuz.repository.MessageRepositoryImpl;

import java.util.function.Function;

public class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {
	@Override
	public void encryptMessage(MessageOuterClass.Message request, StreamObserver<MessageOuterClass.Message> responseObserver) {

		String encryptionMethod = request.getEncryptionMethod();
		String plainText = request.getContent();


		MessageOuterClass.Message req = null;
		if (request.getEncryptionMethod().equals("CaesarCipher")) {
			req = MessageOuterClass.Message.newBuilder()
					.setContent(plainText)
					.setEncryptionMethod(encryptionMethod)
					.setCipherText(actionByMethod(plainText, Encryption.CaesarCipher::encrypt))
					.build();
		}

		if (request.getEncryptionMethod().equals("DES")) {

			req = MessageOuterClass.Message.newBuilder()
					.setContent(plainText)
					.setEncryptionMethod(encryptionMethod)
					.setCipherText(actionByMethod(plainText, Encryption.DES.getInstance()::encrypt))
					.build();
		}


		if (request.getEncryptionMethod().equals("AES")) {
			req = MessageOuterClass.Message.newBuilder()
					.setContent(plainText)
					.setEncryptionMethod(encryptionMethod)
					.setCipherText(actionByMethod(plainText, Encryption.AES.getInstance()::encrypt))
					.build();
		}


		if (req == null) {
			System.out.println("Error in parsing the request message");
			MessageOuterClass.Message resp = MessageOuterClass.Message.newBuilder().build();
			responseObserver.onNext(resp);
			responseObserver.onCompleted();
			return;
		}


		MessageRepository messageRepository = MessageRepositoryImpl.getInstance();
		MessageOuterClass.Message resp = messageRepository.saveMessage(req);
		responseObserver.onNext(resp);
		responseObserver.onCompleted();

	}

	@Override
	public void getMessage(MessageOuterClass.Message request, StreamObserver<MessageOuterClass.Message> responseObserver) {

		int id = request.getId();
		MessageRepository messageRepository = MessageRepositoryImpl.getInstance();
		MessageOuterClass.Message resp = messageRepository.getMessage(id);
		responseObserver.onNext(resp);
		responseObserver.onCompleted();
	}

	@Override
	public void decryptMessage(MessageOuterClass.Message request, StreamObserver<MessageOuterClass.Message> responseObserver) {
		String encryptionMethod = request.getEncryptionMethod();
		String cipherText = request.getCipherText();
		MessageOuterClass.Message req = null;
		if (request.getEncryptionMethod().equals("CaesarCipher")) {
			req = MessageOuterClass.Message.newBuilder()
					.setContent(actionByMethod(cipherText, Encryption.CaesarCipher::decrypt))
					.setEncryptionMethod(encryptionMethod)
					.setCipherText(cipherText)
					.build();
		}
		if (encryptionMethod.equals("DES")) {

			req = MessageOuterClass.Message.newBuilder()
					.setContent(actionByMethod(cipherText, Encryption.DES.getInstance()::decrypt))
					.setEncryptionMethod(encryptionMethod)
					.setCipherText(cipherText)
					.build();
		}
		if (encryptionMethod.equals("AES")) {

			req = MessageOuterClass.Message.newBuilder()
					.setContent(actionByMethod(cipherText, Encryption.AES.getInstance()::decrypt))
					.setEncryptionMethod(encryptionMethod)
					.setCipherText(cipherText)
					.build();
		}

		if (req == null) {
			System.out.println("Error in parsing the request message");
			MessageOuterClass.Message resp = MessageOuterClass.Message.newBuilder().build();
			responseObserver.onNext(resp);
			responseObserver.onCompleted();
			return;
		}

		// will not be saved into the repo, so return the request directly with decrypted plain text.

		responseObserver.onNext(req);
		responseObserver.onCompleted();
	}

	public String actionByMethod(String plainText, Function<String, String> actionMethod) {
		return actionMethod.apply(plainText);
	}


}