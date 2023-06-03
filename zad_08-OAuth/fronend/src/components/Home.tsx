function Home() {
    const user = localStorage.getItem('user')

    return (
        <div>
            <p className="p-10 text-center">Hello {user}</p>
        </div>
    )
}

export default Home;