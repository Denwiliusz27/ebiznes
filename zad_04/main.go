package main

import (
	"github.com/labstack/echo/v4"
	"zad_04/controllers"
)

func main() {
	e := echo.New()

	productsCtrl := controllers.ProductsController{}
	e.GET("/products", productsCtrl.GetAllProdcts)
	e.GET("/products/:name", productsCtrl.GetProduct)
	e.POST("/products/create", productsCtrl.CreateProduct)
	e.PUT("/products/update", productsCtrl.UpdateProduct)
	e.DELETE("/products/delete/:name", productsCtrl.DeleteProduct)

	productsGormCtrl := controllers.NewProductsGormController()
	e.GET("/productsGorm", productsGormCtrl.GetProducts)
	e.GET("/productsGorm/:name", productsGormCtrl.GetProduct)
	e.POST("/productsGorm/create", productsGormCtrl.CreateProduct)
	e.PUT("/productsGorm/update", productsGormCtrl.UpdateProduct)
	e.DELETE("/productsGorm/delete/:name", productsGormCtrl.DeleteProduct)

	e.Logger.Fatal(e.Start(":1323"))
}
