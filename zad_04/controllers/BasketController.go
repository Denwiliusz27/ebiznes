package controllers

import (
	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
	"net/http"
	"zad_04/models"
)

type BasketController struct{}

func (ctrl *BasketController) GetBasketProducts(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	basket := []models.Basket{}

	if err := db.Find(&basket).Error; err != nil {
		return c.String(404, "No products in in basket")
	}

	return c.JSON(http.StatusOK, &basket)
}

func (ctrl *BasketController) GetBasketProduct(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	name := c.Param("name")
	basket := models.Basket{}

	if err := db.Where("name =  ?", name).First(&basket).Error; err != nil {
		return c.String(404, "No product with this name in basket")
	}

	return c.JSON(http.StatusOK, &basket)
}

func (ctrl *BasketController) CreateBasketProduct(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	basket := models.Basket{}

	if err := c.Bind(&basket); err != nil {
		return err
	}

	db.Create(&basket)

	return c.JSON(http.StatusOK, &basket)
}

func (ctrl *BasketController) UpdateBasketProduct(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	basket := models.Basket{}
	dbBasket := models.Basket{}

	if err := c.Bind(&basket); err != nil {
		return err
	}

	if err := db.Where("name =  ?", basket.Name).First(&dbBasket).Error; err != nil {
		return c.String(404, "No product with this name in basket")
	}

	dbBasket.Amount = basket.Amount
	db.Save(&dbBasket)

	return c.JSON(http.StatusOK, &dbBasket)
}

func (ctrl *BasketController) DeleteBasketProduct(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	name := c.Param("name")

	if err := db.Where("name = ?", name).Delete(&models.Basket{}).Error; err != nil {
		return c.String(404, "No product with this name in basket")
	}

	return c.NoContent(http.StatusOK)
}
