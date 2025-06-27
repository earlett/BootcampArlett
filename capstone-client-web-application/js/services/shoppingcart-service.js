let cartService;

class ShoppingCartService {

    cart = {
        items:[],
        total:0
    };

    addToCart(productId)
    {
        const url = `${config.baseUrl}/cart/products/${productId}`;
        const headers = userService.getHeaders();

        axios.post(url, {}, {headers})
            .then(response => {
                this.setCart(response.data)

                this.updateCartDisplay()

            })
            .catch(error => {

                const data = {
                    error: "Add to cart failed."
                };

                templateBuilder.append("error", data, "errors")
            })
    }

    setCart(data)
    {
        this.cart = {
            items: [],
            total: 0
        }

        this.cart.total = data.total;

        for (const [key, value] of Object.entries(data.items)) {
            this.cart.items.push(value);
        }
    }

    loadCart()
    {

        const url = `${config.baseUrl}/cart`;
        const headers = userService.getHeaders();

        axios.get(url, {headers})
            .then(response => {
                this.setCart(response.data)

                this.updateCartDisplay()

            })
            .catch(error => {

                const data = {
                    error: "Load cart failed."
                };

                templateBuilder.append("error", data, "errors")
            })

    }

    loadCartPage()
    {
        const main = document.getElementById("main")
        main.innerHTML = "";

        let div = document.createElement("div");
        div.classList="filter-box";
        main.appendChild(div);

        const contentDiv = document.createElement("div")
        contentDiv.id = "content";
        contentDiv.classList.add("content-form");

        const cartHeader = document.createElement("div")
        cartHeader.classList.add("cart-header")

        const h1 = document.createElement("h1")
        h1.innerText = "Cart";
        cartHeader.appendChild(h1);

        const button = document.createElement("button");
        button.classList.add("btn")
        button.classList.add("btn-danger")
        button.innerText = "Clear";
        button.addEventListener("click", () => this.clearCart());
        cartHeader.appendChild(button)

        contentDiv.appendChild(cartHeader)
        main.appendChild(contentDiv);

        this.cart.items.forEach(item => {
            this.buildItem(item, contentDiv)
        });

        if(this.cart.items.length > 0) {
            const totalDiv = document.createElement("div");
            totalDiv.classList.add("cart-total");
            totalDiv.innerHTML = `<h3>Total: $${this.cart.total}</h3>`;
            contentDiv.appendChild(totalDiv);

            const checkoutButton = document.createElement("button");
            checkoutButton.classList.add("btn", "btn-primary", "btn-lg");
            checkoutButton.innerText = "Proceed to Checkout";
            checkoutButton.addEventListener("click", () => checkoutService.loadCheckoutPage());
            contentDiv.appendChild(checkoutButton);
        } else {
            const emptyDiv = document.createElement("div");
            emptyDiv.classList.add("empty-cart");
            emptyDiv.innerHTML = "<p>Your cart is empty</p>";
            contentDiv.appendChild(emptyDiv);
        }
    }

    buildItem(item, parent)
    {
        let outerDiv = document.createElement("div");
        outerDiv.classList.add("cart-item");

        let div = document.createElement("div");
        outerDiv.appendChild(div);
        let h4 = document.createElement("h4")
        h4.innerText = item.product.name;
        div.appendChild(h4);

        let photoDiv = document.createElement("div");
        photoDiv.classList.add("photo")
        let img = document.createElement("img");
        img.src = `/images/products/${item.product.imageUrl}`
        img.addEventListener("click", () => {
            showImageDetailForm(item.product.name, img.src)
        })
        photoDiv.appendChild(img)
        let priceH4 = document.createElement("h4");
        priceH4.classList.add("price");
        priceH4.innerText = `$${item.product.price}`;
        photoDiv.appendChild(priceH4);
        outerDiv.appendChild(photoDiv);

        let descriptionDiv = document.createElement("div");
        descriptionDiv.innerText = item.product.description;
        outerDiv.appendChild(descriptionDiv);

        let quantityDiv = document.createElement("div")
        quantityDiv.innerText = `Quantity: ${item.quantity}`;
        outerDiv.appendChild(quantityDiv)

        // Add line total
        let lineTotalDiv = document.createElement("div");
        lineTotalDiv.classList.add("line-total");
        lineTotalDiv.innerText = `Line Total: $${item.lineTotal}`;
        outerDiv.appendChild(lineTotalDiv);

        // Add remove button
        let removeButton = document.createElement("button");
        removeButton.classList.add("btn", "btn-sm", "btn-danger");
        removeButton.innerText = "Remove";
        removeButton.addEventListener("click", () => this.removeFromCart(item.product.productId));
        outerDiv.appendChild(removeButton);

        parent.appendChild(outerDiv);
    }

    removeFromCart(productId)
    {
        const url = `${config.baseUrl}/cart/products/${productId}`;
        const headers = userService.getHeaders();

        axios.delete(url, {headers})
            .then(response => {
                this.setCart(response.data)
                this.updateCartDisplay()
                this.loadCartPage()
            })
            .catch(error => {
                const data = {
                    error: "Remove from cart failed."
                };
                templateBuilder.append("error", data, "errors")
            })
    }

    clearCart()
    {

        const url = `${config.baseUrl}/cart`;
        const headers = userService.getHeaders();

        axios.delete(url, {headers})
             .then(response => {
                 this.cart = {
                     items: [],
                     total: 0
                 }

                 this.updateCartDisplay()
                 this.loadCartPage()

             })
             .catch(error => {

                 const data = {
                     error: "Empty cart failed."
                 };

                 templateBuilder.append("error", data, "errors")
             })
    }

    updateCartDisplay()
    {
        try {
            const itemCount = this.cart.items.length;
            const cartControl = document.getElementById("cart-items")

            cartControl.innerText = itemCount;
        }
        catch (e) {

        }
    }
}


document.addEventListener('DOMContentLoaded', () => {
    cartService = new ShoppingCartService();

    if(userService.isLoggedIn())
    {
        cartService.loadCart();
    }

});