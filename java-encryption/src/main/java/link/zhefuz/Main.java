package link.zhefuz;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import link.zhefuz.grpc.MessageServiceImpl;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		int port = 50051;
		Server server = ServerBuilder
				.forPort(port)
				.addService(new MessageServiceImpl())
				.build()
				.start();
		System.out.println("server started, port : " + port);
		server.awaitTermination();


	}
}