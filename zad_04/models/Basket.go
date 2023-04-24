package models

import "gorm.io/gorm"

type Basket struct {
	gorm.Model
	ProductName string
	Amount      int
}
