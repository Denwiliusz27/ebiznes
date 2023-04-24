package main

import (
	"github.com/labstack/echo/v4"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"zad_04/controllers"
	"zad_04/models"
)

func main() {
	e := echo.New()

	db, err := gorm.Open(sqlite.Open("test.db"), &gorm.Config{})
	if err != nil {
		panic("failed to connect to database")
	}

	err = db.AutoMigrate(&models.ProductGorm{})
	err = db.AutoMigrate(&models.Basket{})
	if err != nil {
		panic("failed to connect to database")
	}

	e.Use(func(next echo.HandlerFunc) echo.HandlerFunc {
		return func(c echo.Context) error {
			c.Set("db", db)
			return next(c)
		}
	})

	productsCtrl := controllers.ProductsController{}
	e.GET("/products", productsCtrl.GetAllProdcts)
	e.GET("/products/:name", productsCtrl.GetProduct)
	e.POST("/products/create", productsCtrl.CreateProduct)
	e.PUT("/products/update", productsCtrl.UpdateProduct)
	e.DELETE("/products/delete/:name", productsCtrl.DeleteProduct)

	productsGormCtrl := controllers.ProductsGormController{}
	e.GET("/productsGorm", productsGormCtrl.GetProducts)
	e.GET("/productsGorm/:name", productsGormCtrl.GetProduct)
	e.POST("/productsGorm/create", productsGormCtrl.CreateProduct)
	e.PUT("/productsGorm/update", productsGormCtrl.UpdateProduct)
	e.DELETE("/productsGorm/delete/:name", productsGormCtrl.DeleteProduct)

	basketCtrl := controllers.BasketController{}
	e.GET("/basket", basketCtrl.GetBasketProducts)
	e.GET("/basket/:name", basketCtrl.GetBasketProduct)
	e.POST("/basket/create", basketCtrl.CreateBasketProduct)
	e.PUT("/basket/update", basketCtrl.UpdateBasketProduct)
	e.DELETE("/basket/delete/:name", basketCtrl.DeleteBasketProduct)

	e.Logger.Fatal(e.Start(":1323"))
}
