package controllers

import (
	"github.com/labstack/echo/v4"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"net/http"
	"zad_04/models"
)

type ProductsGormController struct {
	DB *gorm.DB
}

func NewProductsGormController() *ProductsGormController {
	db, err := gorm.Open(sqlite.Open("test.db"), &gorm.Config{})
	if err != nil {
		panic("failed to connect database")
	}

	err = db.AutoMigrate(&models.ProductGorm{})
	if err != nil {
		return nil
	}

	return &ProductsGormController{
		DB: db,
	}
}

func (ctrl *ProductsGormController) GetProducts(c echo.Context) error {
	products := []models.ProductGorm{}
	ctrl.DB.Find(&products)

	return c.JSON(http.StatusOK, &products)
}

func (ctrl *ProductsGormController) GetProduct(c echo.Context) error {
	name := c.Param("name")

	product := models.ProductGorm{}

	if err := ctrl.DB.Where("name =  ?", name).First(&product).Error; err != nil {
		return c.String(404, "No product with this name in database")
	}

	return c.JSON(http.StatusOK, &product)
}

func (ctrl *ProductsGormController) CreateProduct(c echo.Context) error {
	product := models.ProductGorm{}

	if err := c.Bind(&product); err != nil {
		return err
	}

	ctrl.DB.Create(&product)

	return c.JSON(http.StatusOK, &product)
}

func (ctrl *ProductsGormController) UpdateProduct(c echo.Context) error {
	product := models.ProductGorm{}
	dbProduct := models.ProductGorm{}

	if err := c.Bind(&product); err != nil {
		return err
	}

	if err := ctrl.DB.Where("name =  ?", product.Name).First(&dbProduct).Error; err != nil {
		return c.String(404, "No product with this name in database")
	}

	dbProduct.Price = product.Price
	ctrl.DB.Save(&dbProduct)

	return c.JSON(http.StatusOK, &dbProduct)
}

func (ctrl *ProductsGormController) DeleteProduct(c echo.Context) error {
	name := c.Param("name")

	ctrl.DB.Where("name = ?", name).Delete(&models.ProductGorm{})

	return c.NoContent(http.StatusOK)
}
