package main

import (
	"github.com/joho/godotenv"
	"go-server/api"
	"go-server/repository"
	"log"
	"os"
)

func init() {
	err := godotenv.Load()
	if err != nil {
		log.Fatalln(err)
	}
	log.Println(".env loaded")
	repository.Connect()
}

func main() {

	server := &api.Server{}
	server.SetupServer().SetupRoutes().SetupPort(os.Getenv("PORT"))

}
