package com.narify.ecommercy.data.products

import com.narify.ecommercy.model.Category
import com.narify.ecommercy.model.Price
import com.narify.ecommercy.model.Product
import com.narify.ecommercy.model.Rating
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ProductsDataSource {
    suspend fun getProducts(): List<Product>
    fun getProductStream(productId: String): Flow<Product?>
    fun getProductsStream(): Flow<List<Product>>
}

class FakeProductsDataSource @Inject constructor() : ProductsDataSource {

    override suspend fun getProducts(): List<Product> {
        return listOf(product1, product2, product3, product4, product5, product6)
    }

    override fun getProductStream(productId: String): Flow<Product?> = flow {
        val product = getProducts().find { it.id == productId }
        emit(product)
    }

    override fun getProductsStream(): Flow<List<Product>> = flow {
        delay(2000)
        emit(listOf(product1, product2, product3, product4, product5, product6))
    }

    fun getPreviewProducts(): List<Product> {
        return listOf(product1, product2, product3, product4, product5, product6)
    }

    val product1 = Product(
        id = "P1",
        name = "Gaming Headset PS4 Headset with 7.1 Surround Sound, Xbox One Headset with Noise Cancelling Flexible Mic with 2pcs Mic Cover RGB LED Light Memory Earmuffs for PS5, PS4, Xbox one, PC, Nintendo Switch ",
        description = "7.1 Surrounding Stereo Subwoofer & Enter the Game World: Built with powerful 50mm drivers, clear and omnidirectional 7.1 surround sound that gives you a better edge over your opponent, and it allows you to quickly lock the enemy direction and position in an immersive 360 degree sound field, and win the victory quickly.\n" +
                "Noise Cancelling Microphone & 2 pcs Foam Mic Cover: Our ps4 headset is equipped with upgraded noise-cancellng version onmi-directional microphone + foam microphone cover, which can transmits clean and clearer communication and effectively reduce background noise.The mic wind covers can keep your microphone away from saliva and moisture influences to extend the life of the mic. Easily to switch ON/OFF.\n" +
                "Multi-Platform Compatibility with 3.5mm Plug: Our gaming headset support perfectly PS5, PS4, Xbox Series X/S, PC, Switch, Laptop, Mac via the universal 3.5mm wired connection. (Please note you need an extra Microsoft Adapter (Not Included) when connect with an old version Xbox One controller.\n" +
                "All-day Comfort & Cool RGB Light: Our lightest gaming headset ever only at 240 grams. equipped with the premium protein leather memory foam earcups and super soft head beam. the stretchable headband according to the Ergonomic design allows you to find the perfect fit without restrictions, suitable for all gamers of any age. RGB LED lights adds some extra fun and colour to gamer time. Note: The USB is only for led.\n" +
                "Strict Quality Inspection & Friendly Customer Service: Each gaming headset comes with professional after-service. please feel free to contact us if you have any questions.\n",
        images = listOf(
            "https://m.media-amazon.com/images/I/713xpOG8zZL._AC_SL1500_.jpg",
            "https://m.media-amazon.com/images/I/819Ek+5EWLL._AC_SL1500_.jpg",
            "https://m.media-amazon.com/images/I/716X+b47UML._AC_SL1500_.jpg"
        ),
        category = Category("C1", "Headsets"),
        price = Price(
            value = 465.0,
            original = 465.0,
            currency = "EGP",
            symbol = "£",
            raw = "465 £"
        ),
        stock = 10,
        rating = Rating(stars = 4.5F, raters = 9502),
    )

    val product2 = Product(
        id = "P2",
        name = "Atari Mario Playstation 5",
        description = "Designed to perfection\n" +
                "Unparalleled performance\n" +
                "Packed with features\n" +
                "Packed with a host of goodness\n",
        images = listOf(
            "https://m.media-amazon.com/images/I/61zjyRJ2okL._AC_SL1500_.jpg",
            "https://m.media-amazon.com/images/I/41fQiybi-HL._AC_.jpg",
        ),
        category = Category("C2", "Playstation"),
        price = Price(
            value = 699.0,
            original = 699.0,
            currency = "EGP",
            symbol = "£",
            raw = "699 £"
        ),
        stock = 15,
        rating = Rating(stars = 3.5F, raters = 26),
    )

    val product3 = Product(
        id = "P3",
        name = "Sony Playstation Dualshock 4 Wireless Controller - Red",
        description = " Sony Playstation Dualshock 4 Wireless Controller - Red ",
        images = listOf(
            "https://m.media-amazon.com/images/I/619CWyUmtoL._AC_SL1500_.jpg",
        ),
        category = Category("C3", "Playstation"),
        price = Price(
            value = 699.0,
            original = 699.0,
            currency = "EGP",
            symbol = "£",
            raw = "699 £"
        ),
        stock = 15,
        rating = Rating(stars = 3.5F, raters = 26),
    )

    val product4 = Product(
        id = "P4",
        name = "GLURAK Selfie Ring Light, 13 Inches (33 CM) LED Ring Light with Adjustable Tripod Stand and Phone Holder, 3 Light Modes and 10 Brightness Level for Live Stream, Makeup, YouTube, TikTok, Photography",
        description = "GLURAK Selfie Ring Light, 13 Inches (33 CM) LED Ring Light with Adjustable Tripod Stand and Phone Holder, 3 Light Modes and 10 Brightness Level for Live Stream, Makeup, YouTube, TikTok, Photography",
        images = listOf(
            "https://m.media-amazon.com/images/I/51rJEls8i1L._AC_SL1309_.jpg",
        ),
        category = Category("C3", "Playstation"),
        price = Price(
            value = 699.0,
            original = 699.0,
            currency = "EGP",
            symbol = "£",
            raw = "699 £"
        ),
        stock = 15,
        rating = Rating(stars = 3.5F, raters = 26),
    )

    val product5 = Product(
        id = "P5",
        name = "Cosmoplast 3 Tiers Multipurpose Storage Cabinet With Wheels, Dark Red With Translucent Drawers",
        description = "Cosmoplast 3 Tiers Multipurpose Storage Cabinet With Wheels, Dark Red With Translucent Drawers",
        images = listOf(
            "https://m.media-amazon.com/images/I/71DTeZjOn3L._AC_SL1500_.jpg",
        ),
        category = Category("C3", "Playstation"),
        price = Price(
            value = 699.0,
            original = 699.0,
            currency = "EGP",
            symbol = "£",
            raw = "699 £"
        ),
        stock = 15,
        rating = Rating(stars = 3.5F, raters = 26),
    )

    val product6 = Product(
        id = "P6",
        name = "Samsung Galaxy A34 Dual SIM Mobile Phone Android, 8GB RAM, 128GB, Awesome Graphite",
        description = "Samsung Galaxy A34 Dual SIM Mobile Phone Android, 8GB RAM, 128GB, Awesome Graphite",
        images = listOf(
            "https://m.media-amazon.com/images/I/71RqyKk2eBL._AC_SL1500_.jpg",
        ),
        category = Category("C3", "Playstation"),
        price = Price(
            value = 11666.0,
            original = 11666.0,
            currency = "EGP",
            symbol = "£",
            raw = "11666 £"
        ),
        stock = 15,
        rating = Rating(stars = 3.5F, raters = 26),
    )

}

