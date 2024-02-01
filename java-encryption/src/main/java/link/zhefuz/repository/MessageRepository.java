package link.zhefuz.repository;

import link.zhefuz.grpc.MessageOuterClass;

public interface MessageRepository {
	MessageOuterClass.Message saveMessage(MessageOuterClass.Message message);

	MessageOuterClass.Message getMessage(int id);
}
