package models

import (
	"gorm.io/gorm"
)

type ProductGorm struct {
	gorm.Model
	Name  string `json:"name"`
	Price int    `json:"price"`
}
