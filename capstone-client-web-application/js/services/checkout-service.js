   let checkoutService;

   class CheckoutService {

       checkout() {
           const url = `${config.baseUrl}/orders`;
           const headers = userService.getHeaders();

           // Show loading state
           const button = event.target;
           const originalText = button.innerText;
           button.disabled = true;
           button.innerText = "Processing...";

           axios.post(url, {}, {headers})
               .then(response => {
                   // Clear cart display
                   cartService.cart = {
                       items: [],
                       total: 0
                   };
                   cartService.updateCartDisplay();

                   // Show success message
                   const data = {
                       message: `Order #${response.data.orderId} has been placed successfully! Total: $${response.data.shippingAmount > 0 ?
                           (parseFloat(cartService.cart.total) + parseFloat(response.data.shippingAmount)).toFixed(2) :
                           cartService.cart.total}`
                   };
                   templateBuilder.append("message", data, "errors");

                   // Redirect to home page after 3 seconds
                   setTimeout(() => {
                       loadProducts();
                   }, 3000);
               })
               .catch(error => {
                   let errorMessage = "Checkout failed.";

                   if (error.response && error.response.data && error.response.data.message) {
                       errorMessage = error.response.data.message;
                   }

                   const data = {
                       error: errorMessage
                   };
                   templateBuilder.append("error", data, "errors");

                   // Reset button
                   button.disabled = false;
                   button.innerText = originalText;
               });
       }

       loadCheckoutPage() {
           // First check if cart has items
           if (!cartService.cart.items || cartService.cart.items.length === 0) {
               const data = {
                   error: "Your cart is empty. Add items before checkout."
               };
               templateBuilder.append("error", data, "errors");
               return;
           }

           const main = document.getElementById("main");
           main.innerHTML = "";

           let div = document.createElement("div");
           div.classList = "filter-box";
           main.appendChild(div);

           const contentDiv = document.createElement("div");
           contentDiv.id = "content";
           contentDiv.classList.add("content-form");

           const h1 = document.createElement("h1");
           h1.innerText = "Checkout";
           contentDiv.appendChild(h1);

           // Order Summary
           const summaryDiv = document.createElement("div");
           summaryDiv.classList.add("order-summary");
           summaryDiv.innerHTML = "<h3>Order Summary</h3>";

           cartService.cart.items.forEach(item => {
               const itemDiv = document.createElement("div");
               itemDiv.classList.add("order-item");
               itemDiv.innerHTML = `
                   <div>${item.product.name} x ${item.quantity}</div>
                   <div>$${(item.product.price * item.quantity).toFixed(2)}</div>
               `;
               summaryDiv.appendChild(itemDiv);
           });

           // Shipping calculation
           const cartTotal = parseFloat(cartService.cart.total);
           const shippingCost = cartTotal >= 50 ? 0 : 5;
           const finalTotal = cartTotal + shippingCost;

           const shippingDiv = document.createElement("div");
           shippingDiv.classList.add("order-item");
           shippingDiv.innerHTML = `
               <div>Shipping ${cartTotal >= 50 ? '(Free over $50)' : ''}</div>
               <div>$${shippingCost.toFixed(2)}</div>
           `;
           summaryDiv.appendChild(shippingDiv);

           const totalDiv = document.createElement("div");
           totalDiv.classList.add("order-total");
           totalDiv.innerHTML = `
               <h3>Total: $${finalTotal.toFixed(2)}</h3>
           `;
           summaryDiv.appendChild(totalDiv);

           contentDiv.appendChild(summaryDiv);

           // Shipping Address (loaded from profile)
           const addressDiv = document.createElement("div");
           addressDiv.classList.add("shipping-address");
           addressDiv.innerHTML = "<h3>Shipping Address</h3><p>Loading profile...</p>";
           contentDiv.appendChild(addressDiv);

           // Load profile to show shipping address
           const profileUrl = `${config.baseUrl}/profile`;
           const headers = userService.getHeaders();

           axios.get(profileUrl, {headers})
               .then(response => {
                   const profile = response.data;
                   addressDiv.innerHTML = `
                       <h3>Shipping Address</h3>
                       <p>${profile.firstName} ${profile.lastName}</p>
                       <p>${profile.address}</p>
                       <p>${profile.city}, ${profile.state} ${profile.zip}</p>
                       <p>Phone: ${profile.phone}</p>
                       <p class="text-muted">Update your profile to change shipping address</p>
                   `;
               })
               .catch(error => {
                   addressDiv.innerHTML = `
                       <h3>Shipping Address</h3>
                       <p class="text-danger">Please update your profile before checkout</p>
                   `;
               });

           // Checkout button
           const button = document.createElement("button");
           button.classList.add("btn", "btn-primary", "btn-lg");
           button.innerText = "Place Order";
           button.addEventListener("click", () => this.checkout());
           contentDiv.appendChild(button);

           main.appendChild(contentDiv);
       }
   }

   document.addEventListener('DOMContentLoaded', () => {
       checkoutService = new CheckoutService();
   });