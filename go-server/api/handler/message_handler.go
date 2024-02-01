package handler

import (
	"github.com/gofiber/fiber/v2"
	"go-server/grpc"
	"log"
	"strconv"
)

func CreateMessage(ctx *fiber.Ctx) error {
	data := make(map[string]any)
	// parse json data from request body
	if err := ctx.BodyParser(&data); err != nil {
		log.Println(err)
	}
	if data["content"] == nil || data["encryption_method"] == nil {
		return ctx.Status(fiber.StatusBadRequest).JSON(fiber.Map{"message": "Please input message content and encryption method"})
	}

	msg := &grpc.Message{
		Content:          data["content"].(string),
		EncryptionMethod: data["encryption_method"].(string),
	}
	resp, err := grpc.GetMessage(msg, "Encrypt")
	if err != nil {
		return ctx.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"message": "Error"})
	}

	return ctx.Status(fiber.StatusOK).JSON(resp)
}

func DecryptMessage(ctx *fiber.Ctx) error {
	data := make(map[string]any)
	// parse json data from request body
	if err := ctx.BodyParser(&data); err != nil {
		log.Println(err)
	}
	if data["cipher_text"] == nil || data["encryption_method"] == nil {
		return ctx.Status(fiber.StatusBadRequest).JSON(fiber.Map{"message": "Please input cipher text"})
	}

	msg := &grpc.Message{
		CipherText:       data["cipher_text"].(string),
		EncryptionMethod: data["encryption_method"].(string),
	}
	resp, err := grpc.GetMessage(msg, "Decrypt")
	if err != nil {
		return ctx.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"message": "Error"})
	}

	return ctx.Status(fiber.StatusOK).JSON(resp)
}
func GetMessage(ctx *fiber.Ctx) error {
	data := make(map[string]any)
	// parse json data from request body
	if err := ctx.BodyParser(&data); err != nil {
		log.Println(err)
	}
	if data["id"] == nil {
		return ctx.Status(fiber.StatusBadRequest).JSON(fiber.Map{"message": "Please input valid id"})
	}
	value, err := strconv.ParseUint(data["id"].(string), 10, 32)
	if err != nil {
		return ctx.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"message": "Error"})
	}

	msg := &grpc.Message{
		Id: uint32(value),
	}
	resp, err := grpc.GetMessage(msg, "Get")
	if err != nil {
		return ctx.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"message": "Error"})
	}

	return ctx.Status(fiber.StatusOK).JSON(resp)
}
