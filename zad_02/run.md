docker run --rm -it -p 9000:9000 -v ${PWD}:/home/kprzystalski/projekt/ kprzystalski/ebiznes2021:latest
cd play-scala-seed
sbt run