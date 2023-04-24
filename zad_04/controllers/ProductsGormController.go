package controllers

import (
	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
	"net/http"
	"zad_04/models"
)

type ProductsGormController struct{}

func (ctrl *ProductsGormController) GetProducts(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	products := []models.ProductGorm{}
	db.Find(&products)

	return c.JSON(http.StatusOK, &products)
}

func (ctrl *ProductsGormController) GetProduct(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	name := c.Param("name")
	product := models.ProductGorm{}

	if err := db.Where("name =  ?", name).First(&product).Error; err != nil {
		return c.String(404, "No product with this name in database")
	}

	return c.JSON(http.StatusOK, &product)
}

func (ctrl *ProductsGormController) CreateProduct(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	product := models.ProductGorm{}

	if err := c.Bind(&product); err != nil {
		return err
	}

	db.Create(&product)

	return c.JSON(http.StatusOK, &product)
}

func (ctrl *ProductsGormController) UpdateProduct(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	product := models.ProductGorm{}
	dbProduct := models.ProductGorm{}

	if err := c.Bind(&product); err != nil {
		return err
	}

	if err := db.Where("name =  ?", product.Name).First(&dbProduct).Error; err != nil {
		return c.String(404, "No product with this name in database")
	}

	dbProduct.Price = product.Price
	db.Save(&dbProduct)

	return c.JSON(http.StatusOK, &dbProduct)
}

func (ctrl *ProductsGormController) DeleteProduct(c echo.Context) error {
	db := c.Get("db").(*gorm.DB)
	name := c.Param("name")

	if err := db.Where("name = ?", name).Delete(&models.ProductGorm{}).Error; err != nil {
		return c.String(404, "No product with this name in database")
	}

	return c.NoContent(http.StatusOK)
}
