package grpc

import (
	"github.com/gofiber/fiber/v2/log"
	"google.golang.org/grpc/credentials/insecure"
	"os"

	"golang.org/x/net/context"
	"google.golang.org/grpc"
)

func GetMessage(message *Message, method string) (*Message, error) {
	var dest string
	if os.Getenv("ENVIRONMENT") == "development" {
		dest = "localhost"
	} else {
		dest = "java-encryption"
	}
	conn, err := grpc.Dial(dest+":50051", grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		log.Fatal("Did not connect: %v", err)
	}
	defer func(conn *grpc.ClientConn) {
		err := conn.Close()
		if err != nil {
			log.Fatal("Did not close: %v", err)
		}
	}(conn)
	client := NewMessageServiceClient(conn)
	// connect()

	if method == "Encrypt" {
		resp, err := client.EncryptMessage(context.Background(), message)
		if err != nil {
			log.Fatalf("Error while calling EncryptMessage: %v", err)
			return nil, err
		}

		return resp, nil
	}

	if method == "Decrypt" {

		resp, err := client.DecryptMessage(context.Background(), message)
		if err != nil {
			log.Fatalf("Error while calling DecryptMessage: %v", err)
			return nil, err
		}

		return resp, nil
	}
	if method == "Get" {
		resp, err := client.GetMessage(context.Background(), message)
		if err != nil {
			log.Fatalf("Error while calling DecryptMessage: %v", err)
			return nil, err
		}
		return resp, nil
	}
	return nil, nil
}
