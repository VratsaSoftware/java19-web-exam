function setOnAddProductClick() {
    $(".addBtn").click((event) => {
        let addBtn = $(event.target);
        let productId = addBtn.attr("data-id")
        let quantity = addBtn.prev().val()

        addProduct(productId, quantity)
    })
}

function addProduct(productId, quantity) {
    let data = { productId, quantity }
    $.ajax({
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        url: "/cart/add",
        success: function (data) {
            console.log(data)
        }
    })
}

function setOnUpdateProductClick() {
    $(".updateBtn").click((event) => {
        let addBtn = $(event.target);
        let productId = addBtn.attr("data-id")
        let quantity = addBtn.prev().val()

        updateProduct(productId, quantity)
    })
}

function updateProduct(productId, quantity) {
    let data = { productId, quantity }
    console.log(data)
    $.ajax({
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        url: "/cart/update",
        success: function (data) {
            onCartChangeSuccess(data)
        }
    })
}

function setOnDiscardProductClick() {
    $(".discardBtn").click((event) => {
        let discardBtn = $(event.target);
        let productId = discardBtn.attr("data-id");

        discardProduct(productId)
    })
}

function discardProduct(productId) {
    let data = { productId };
    $.ajax({
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        url: "/cart/discard",
        success: function (data) {
            onCartChangeSuccess(data)
        }
    })
}

function onCartChangeSuccess(cart) {
    console.log(cart)
    $("#txtTotal").text("Total: $" + parseFloat(cart.total).toFixed(2))
    let productsList = "";
    for (let product of cart.products) {
        productsList += createProductListing(product);
    }

    $("#productsList").html(productsList)

    setOnUpdateProductClick()
    setOnDiscardProductClick()
}

function createProductListing(product) {
    return `
        <div>
            <h3>${product.product.name}</h3>
            <h4>$${product.product.price}</h4>
            <h4>Cost: $${parseFloat(product.cost).toFixed(2)}</h4>
        </div>
        <input value="${product.count}" type="number" min="0" max="2000000" style="display: inline-block" />
        <input type="button" class="updateBtn" value="Update" data-id="${product.product.id}"/>
        <input type="button" class="discardBtn" value="Discard" data-id="${product.product.id}"/>
        <hr/>
`
}
