package api

import (
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/cors"
	jwtware "github.com/gofiber/jwt/v2"
	"go-server/api/handler"
	"log"
	"os"
)

type Server struct {
	app *fiber.App
}

func (server *Server) SetupServer() *Server {
	server.app = fiber.New()
	return server
}

func (server *Server) SetupRoutes() *Server {
	// allow cors when in dev mode
	server.app.Use(cors.New(cors.Config{
		AllowOriginsFunc: func(origin string) bool {
			return os.Getenv("ENVIRONMENT") == "development"
		},
	}))
	// open apis
	// server.app.Post("/api/v1/register", handler.Register)
	server.app.Post("/api/v1/login", handler.Login)
	// protected apis
	protected := server.app.Group("/api/v1/protected")
	protected.Use(jwtware.New(jwtware.Config{
		SigningMethod: "HS512",
		SigningKey:    []byte(os.Getenv("SECRETKEY")),
	}))
	protected.Post("/create-message", handler.CreateMessage)
	protected.Post("/decrypt-message", handler.DecryptMessage)
	protected.Post("/get-message", handler.GetMessage)

	// static root
	server.app.Static("/", "./public")
	server.app.Get("*", func(c *fiber.Ctx) error {
		return c.SendFile("./public/index.html")
	})
	return server
}

func (server *Server) SetupPort(port string) *Server {
	if os.Getenv("ENVIRONMENT") != "development" {
		log.Fatalln(server.app.ListenTLS(":443", "server.crt", "server.key"))
	} else {
		log.Fatalln(server.app.Listen(":80"))
	}
	return server
}
