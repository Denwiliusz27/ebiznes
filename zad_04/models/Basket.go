package models

import "gorm.io/gorm"

type Basket struct {
	gorm.Model
	Name   string `json:"name"`
	Amount int    `json:"amount"`
}
