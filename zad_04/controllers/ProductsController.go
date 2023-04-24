package controllers

import (
	"github.com/labstack/echo/v4"
	"net/http"
	"zad_04/models"
)

var products = []models.Product{
	{
		Name:  "bread",
		Price: 4,
	},
	{
		Name:  "water",
		Price: 2,
	},
	{
		Name:  "notebook",
		Price: 9,
	},
	{
		Name:  "eggs",
		Price: 10,
	},
}

type ProductsController struct{}

func (ctrl *ProductsController) GetAllProdcts(c echo.Context) error {
	return c.JSON(http.StatusOK, products)
}

func (ctrl *ProductsController) GetProduct(c echo.Context) error {
	name := c.Param("name")

	for i := 0; i < len(products); i++ {
		if products[i].Name == name {
			return c.JSON(http.StatusOK, products[i])
		}
	}

	return c.JSON(http.StatusNotFound, nil)
}

func (ctrl *ProductsController) CreateProduct(c echo.Context) error {
	product := models.Product{}

	if err := c.Bind(&product); err != nil {
		return err
	}

	products = append(products, product)
	return c.JSON(http.StatusOK, product)
}

func (ctrl *ProductsController) UpdateProduct(c echo.Context) error {
	product := models.Product{}

	if err := c.Bind(&product); err != nil {
		return err
	}

	for i := 0; i < len(products); i++ {
		if products[i].Name == product.Name {
			products[i] = product
			return c.JSON(http.StatusOK, products[i])
		}
	}

	return c.JSON(http.StatusNotFound, nil)
}

func (ctrl *ProductsController) DeleteProduct(c echo.Context) error {
	name := c.Param("name")

	for i := 0; i < len(products); i++ {
		if products[i].Name == name {
			products = append(products[:i], products[i+1:]...)
			return c.JSON(http.StatusOK, products)
		}
	}

	return c.JSON(http.StatusNotFound, nil)
}
