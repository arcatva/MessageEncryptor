package repository

import (
	"go-server/model"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"log"
	"os"
)

var DB *gorm.DB

func Connect() {
	dsn := os.Getenv("DSN")
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatalln(err)
	}
	DB = db
	err = DB.AutoMigrate(
		&model.User{},
	)
	if err != nil {
		log.Fatalln(err)
	}
	log.Println("Database loaded")
	user := &model.User{
		Email:     "testadmin@admin.lab",
		FirstName: "testadmin",
		LastName:  "testadmin",
	}
	if err := user.SetPassword("pA1zsWO10FDsBd"); err != nil {
		log.Println("Failed to set password")
	}
	DB.Create(user)
	log.Println("Initialized admin user")
}
