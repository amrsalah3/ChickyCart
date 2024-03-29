package com.narify.chickycart.data.products

import com.narify.chickycart.BuildConfig
import com.narify.chickycart.model.entities.ProductEntity
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min

@Singleton
class ProductApiMockDataSource @Inject constructor() {

    suspend fun getProducts(
        searchQuery: String? = null,
        category: String? = null,
        sortType: String? = null,
        featuredProductsOnly: Boolean = false,
        startAt: Int = 0,
        limit: Int = DEFAULT_LIMIT
    ): List<ProductEntity> {
        delay(1000)

        var searchResult = productEntities
        if (!searchQuery.isNullOrBlank()) {
            searchResult = productEntities.filter { it.title.contains(searchQuery, true) }
        }

        var filteredProducts = searchResult
        if (!category.isNullOrBlank()) {
            filteredProducts = searchResult.filter { it.category.equals(category, true) }
        }

        var sortedProducts = filteredProducts
        if (!sortType.isNullOrBlank()) {
            sortedProducts = when (sortType) {
                SortType.ALPHABETICAL -> filteredProducts.sortedBy { it.title }
                SortType.PRICE -> filteredProducts.sortedBy { it.price }
                SortType.RATING -> filteredProducts.sortedByDescending { it.rating }
                else -> filteredProducts
            }
        }

        var resultProducts = sortedProducts
        if (featuredProductsOnly) {
            resultProducts = sortedProducts.filter { it.isDiscountActive }
        }

        if (startAt > resultProducts.lastIndex) return emptyList()

        val fromIndex = startAt
        val toIndex = min(startAt + limit, resultProducts.size)

        val response = resultProducts.subList(fromIndex, toIndex)

        return response
    }

    suspend fun getProduct(productId: String): ProductEntity? {
        delay(1000)
        val response = productEntities.find { it.id == productId.toInt() }
        return response
    }

    companion object {
        /**
         * Default number of products to be fetched per load.
         */
        const val DEFAULT_LIMIT = 10
    }

    /**
     * Sort type string values for this specific data source [ProductApiMockDataSource]
     */
    object SortType {
        const val ALPHABETICAL = "alphabetical"
        const val PRICE = "price"
        const val RATING = "rating"
        const val NONE = ""
    }

    private val baseUrl = BuildConfig.PRODUCT_IMAGES_BASE_URL
    private val productEntities: List<ProductEntity> = listOf(
        ProductEntity(
            id = 1,
            title = "iPhone 9",
            description = "An apple mobile which is nothing like apple",
            price = 549.0,
            discountPercentage = 12.0,
            rating = 4.69,
            stock = 94,
            brand = "Apple",
            category = "Smartphones",
            thumbnail = "$baseUrl/1/thumbnail.jpg",
            images = listOf(
                "$baseUrl/1/1.jpg",
                "$baseUrl/1/2.jpg",
                "$baseUrl/1/3.jpg"
            )
        ),
        ProductEntity(
            id = 2,
            title = "iPhone X",
            description = "SIM-Free, Model A19211 6.5-inch Super Retina HD display with OLED technology A12 Bionic chip with ...",
            price = 899.0,
            discountPercentage = 17.0,
            rating = 4.44,
            stock = 34,
            brand = "Apple",
            category = "Smartphones",
            thumbnail = "$baseUrl/2/thumbnail.jpg",
            images = listOf(
                "$baseUrl/2/1.jpg",
                "$baseUrl/2/2.jpg",
                "$baseUrl/2/3.jpg",
                "$baseUrl/2/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 3,
            title = "Samsung Universe 9",
            description = "Samsung's new variant which goes beyond Galaxy to the Universe",
            price = 1249.0,
            discountPercentage = 15.0,
            rating = 4.09,
            stock = 36,
            brand = "Samsung",
            category = "Smartphones",
            thumbnail = "$baseUrl/3/thumbnail.jpg",
            images = listOf("$baseUrl/3/1.jpg")
        ),
        ProductEntity(
            id = 4,
            title = "OPPOF19",
            description = "OPPO F19 is officially announced on April 2021.",
            price = 280.0,
            discountPercentage = 17.0,
            rating = 4.3,
            stock = 123,
            brand = "OPPO",
            category = "Smartphones",
            thumbnail = "$baseUrl/4/thumbnail.jpg",
            images = listOf(
                "$baseUrl/4/1.jpg",
                "$baseUrl/4/2.jpg",
                "$baseUrl/4/3.jpg",
                "$baseUrl/4/4.jpg",
                "$baseUrl/4/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 5,
            title = "Huawei P30",
            description = "Huawei’s re-badged P30 Pro New Edition was officially unveiled yesterday in Germany and now the device has made its way to the UK.",
            price = 499.0,
            discountPercentage = 10.0,
            rating = 4.09,
            stock = 32,
            brand = "Huawei",
            category = "Smartphones",
            thumbnail = "$baseUrl/5/thumbnail.jpg",
            images = listOf(
                "$baseUrl/5/1.jpg",
                "$baseUrl/5/2.jpg",
                "$baseUrl/5/3.jpg"
            )
        ),
        ProductEntity(
            id = 6,
            title = "MacBook Pro",
            description = "MacBook Pro 2021 with mini-LED display may launch between September, November",
            price = 1749.0,
            discountPercentage = 11.0,
            rating = 4.57,
            stock = 83,
            brand = "Apple",
            category = "Laptops",
            thumbnail = "$baseUrl/6/thumbnail.png",
            images = listOf(
                "$baseUrl/6/1.png",
                "$baseUrl/6/2.jpg",
                "$baseUrl/6/3.png",
                "$baseUrl/6/4.jpg"
            )
        ),
        ProductEntity(
            id = 7,
            title = "Samsung Galaxy Book",
            description = "Samsung Galaxy Book S (2020) Laptop With Intel Lakefield Chip, 8GB of RAM Launched",
            price = 1499.0,
            discountPercentage = 4.0,
            rating = 4.25,
            stock = 50,
            brand = "Samsung",
            category = "Laptops",
            thumbnail = "$baseUrl/7/thumbnail.jpg",
            images = listOf(
                "$baseUrl/7/1.jpg",
                "$baseUrl/7/2.jpg",
                "$baseUrl/7/3.jpg",
                "$baseUrl/7/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 8,
            title = "Microsoft Surface Laptop 4",
            description = "Style and speed. Stand out on HD video calls backed by Studio Mics. Capture ideas on the vibrant touchscreen.",
            price = 1499.0,
            discountPercentage = 10.0,
            rating = 4.43,
            stock = 68,
            brand = "Microsoft Surface",
            category = "Laptops",
            thumbnail = "$baseUrl/8/thumbnail.jpg",
            images = listOf(
                "$baseUrl/8/1.jpg",
                "$baseUrl/8/2.jpg",
                "$baseUrl/8/3.jpg",
                "$baseUrl/8/4.jpg",
                "$baseUrl/8/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 9,
            title = "Infinix INBOOK",
            description = "Infinix Inbook X1 Ci3 10th 8GB 256GB 14 Win10 Grey – 1 Year Warranty",
            price = 1099.0,
            discountPercentage = 11.0,
            rating = 4.54,
            stock = 96,
            brand = "Infinix",
            category = "Laptops",
            thumbnail = "$baseUrl/9/thumbnail.jpg",
            images = listOf(
                "$baseUrl/9/1.jpg",
                "$baseUrl/9/2.png",
                "$baseUrl/9/3.png",
                "$baseUrl/9/4.jpg",
                "$baseUrl/9/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 10,
            title = "HP Pavilion 15-DK1056WM",
            description = "HP Pavilion 15-DK1056WM Gaming Laptop 10th Gen Core i5, 8GB, 256GB SSD, GTX 1650 4GB, Windows 10",
            price = 1099.0,
            discountPercentage = 6.0,
            rating = 4.43,
            stock = 89,
            brand = "HP Pavilion",
            category = "Laptops",
            thumbnail = "$baseUrl/10/thumbnail.jpeg",
            images = listOf(
                "$baseUrl/10/1.jpg",
                "$baseUrl/10/2.jpg",
                "$baseUrl/10/3.jpg",
                "$baseUrl/10/thumbnail.jpeg"
            )
        ),
        ProductEntity(
            id = 11,
            title = "perfume Oil",
            description = "Mega Discount, Impression of Acqua Di Gio by GiorgioArmani concentrated attar perfume Oil",
            price = 13.0,
            discountPercentage = 8.0,
            rating = 4.26,
            stock = 65,
            brand = "Impression of Acqua Di Gio",
            category = "Fragrances",
            thumbnail = "$baseUrl/11/thumbnail.jpg",
            images = listOf(
                "$baseUrl/11/1.jpg",
                "$baseUrl/11/2.jpg",
                "$baseUrl/11/3.jpg",
                "$baseUrl/11/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 12,
            title = "Brown Perfume",
            description = "Royal_Mirage Sport Brown Perfume for Men & Women - 120ml",
            price = 40.0,
            discountPercentage = 15.0,
            rating = 4.0,
            stock = 52,
            brand = "Royal_Mirage",
            category = "Fragrances",
            thumbnail = "$baseUrl/12/thumbnail.jpg",
            images = listOf(
                "$baseUrl/12/1.jpg",
                "$baseUrl/12/2.jpg",
                "$baseUrl/12/3.png",
                "$baseUrl/12/4.jpg",
                "$baseUrl/12/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 13,
            title = "Fog Scent Xpressio Perfume",
            description = "Product details of Best Fog Scent Xpressio Perfume 100ml For Men cool long lasting perfumes for Men",
            price = 13.0,
            discountPercentage = 8.0,
            rating = 4.59,
            stock = 61,
            brand = "Fog Scent Xpressio",
            category = "Fragrances",
            thumbnail = "$baseUrl/13/thumbnail.webp",
            images = listOf(
                "$baseUrl/13/1.jpg",
                "$baseUrl/13/2.png",
                "$baseUrl/13/3.jpg",
                "$baseUrl/13/4.jpg",
                "$baseUrl/13/thumbnail.webp"
            )
        ),
        ProductEntity(
            id = 14,
            title = "Non-Alcoholic Concentrated Perfume Oil",
            description = "Original Al Munakh® by Mahal Al Musk | Our Impression of Climate | 6ml Non-Alcoholic Concentrated Perfume Oil",
            price = 120.0,
            discountPercentage = 15.0,
            rating = 4.21,
            stock = 114,
            brand = "Al Munakh",
            category = "Fragrances",
            thumbnail = "$baseUrl/14/thumbnail.jpg",
            images = listOf(
                "$baseUrl/14/1.jpg",
                "$baseUrl/14/2.jpg",
                "$baseUrl/14/3.jpg",
                "$baseUrl/14/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 15,
            title = "Eau De Perfume Spray",
            description = "Genuine  Al-Rehab spray perfume from UAE/Saudi Arabia/Yemen High Quality",
            price = 30.0,
            discountPercentage = 10.0,
            rating = 4.7,
            stock = 105,
            brand = "Lord - Al-Rehab",
            category = "Fragrances",
            thumbnail = "$baseUrl/15/thumbnail.jpg",
            images = listOf(
                "$baseUrl/15/1.jpg",
                "$baseUrl/15/2.jpg",
                "$baseUrl/15/3.jpg",
                "$baseUrl/15/4.jpg",
                "$baseUrl/15/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 16,
            title = "Hyaluronic Acid Serum",
            description = "L'OrÃ©al Paris introduces Hyaluron Expert Replumping Serum formulated with 1.5% Hyaluronic Acid",
            price = 19.0,
            discountPercentage = 13.0,
            rating = 4.83,
            stock = 110,
            brand = "L'Oreal Paris",
            category = "Skincare",
            thumbnail = "$baseUrl/16/thumbnail.jpg",
            images = listOf(
                "$baseUrl/16/1.png",
                "$baseUrl/16/2.webp",
                "$baseUrl/16/3.jpg",
                "$baseUrl/16/4.jpg",
                "$baseUrl/16/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 17,
            title = "Tree Oil 30ml",
            description = "Tea tree oil contains a number of compounds, including terpinen-4-ol, that have been shown to kill certain bacteria,",
            price = 12.0,
            discountPercentage = 4.0,
            rating = 4.52,
            stock = 78,
            brand = "Hemani Tea",
            category = "Skincare",
            thumbnail = "$baseUrl/17/thumbnail.jpg",
            images = listOf(
                "$baseUrl/17/1.jpg",
                "$baseUrl/17/2.jpg",
                "$baseUrl/17/3.jpg",
                "$baseUrl/17/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 18,
            title = "Oil Free Moisturizer 100ml",
            description = "Dermive Oil Free Moisturizer with SPF 20 is specifically formulated with ceramides, hyaluronic acid & sunscreen.",
            price = 40.0,
            discountPercentage = 13.0,
            rating = 4.56,
            stock = 88,
            brand = "Dermive",
            category = "Skincare",
            thumbnail = "$baseUrl/18/thumbnail.jpg",
            images = listOf(
                "$baseUrl/18/1.jpg",
                "$baseUrl/18/2.jpg",
                "$baseUrl/18/3.jpg",
                "$baseUrl/18/4.jpg",
                "$baseUrl/18/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 19,
            title = "Skin Beauty Serum.",
            description = "Product name: rorec collagen hyaluronic acid white face serum riceNet weight: 15 m",
            price = 46.0,
            discountPercentage = 10.0,
            rating = 4.42,
            stock = 54,
            brand = "ROREC White Rice",
            category = "Skincare",
            thumbnail = "$baseUrl/19/thumbnail.jpg",
            images = listOf(
                "$baseUrl/19/1.jpg",
                "$baseUrl/19/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 20,
            title = "Freckle Treatment Cream- 15gm",
            description = "Fair & Clear is Pakistan's only pure Freckle cream which helpsfade Freckles, Darkspots and pigments. Mercury level is 0%, so there are no side effects.",
            price = 70.0,
            discountPercentage = 16.0,
            rating = 4.06,
            stock = 140,
            brand = "Fair & Clear",
            category = "Skincare",
            thumbnail = "$baseUrl/20/thumbnail.jpg",
            images = listOf(
                "$baseUrl/20/1.jpg",
                "$baseUrl/20/2.jpg"
            )
        ),
        ProductEntity(
            id = 21,
            title = "- Daal Masoor 500 grams",
            description = "Fine quality Branded Product Keep in a cool and dry place",
            price = 20.0,
            discountPercentage = 4.0,
            rating = 4.44,
            stock = 133,
            brand = "Saaf & Khaas",
            category = "Groceries",
            thumbnail = "$baseUrl/21/thumbnail.png",
            images = listOf(
                "$baseUrl/21/1.png",
                "$baseUrl/21/2.jpg",
                "$baseUrl/21/3.jpg"
            )
        ),
        ProductEntity(
            id = 22,
            title = "Elbow Macaroni - 400 gm",
            description = "Product details of Bake Parlor Big Elbow Macaroni - 400 gm",
            price = 14.0,
            discountPercentage = 15.0,
            rating = 4.57,
            stock = 146,
            brand = "Bake Parlor Big",
            category = "Groceries",
            thumbnail = "$baseUrl/22/thumbnail.jpg",
            images = listOf(
                "$baseUrl/22/1.jpg",
                "$baseUrl/22/2.jpg",
                "$baseUrl/22/3.jpg"
            )
        ),
        ProductEntity(
            id = 23,
            title = "Orange Essence Food Flavou",
            description = "Specifications of Orange Essence Food Flavour For Cakes and Baking Food Item",
            price = 14.0,
            discountPercentage = 8.0,
            rating = 4.85,
            stock = 26,
            brand = "Baking Food Items",
            category = "Groceries",
            thumbnail = "$baseUrl/23/thumbnail.jpg",
            images = listOf(
                "$baseUrl/23/1.jpg",
                "$baseUrl/23/2.jpg",
                "$baseUrl/23/3.jpg",
                "$baseUrl/23/4.jpg",
                "$baseUrl/23/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 24,
            title = "cereals muesli fruit nuts",
            description = "original fauji cereal muesli 250gm box pack original fauji cereals muesli fruit nuts flakes breakfast cereal break fast faujicereals cerels cerel foji fouji",
            price = 46.0,
            discountPercentage = 16.0,
            rating = 4.94,
            stock = 113,
            brand = "fauji",
            category = "Groceries",
            thumbnail = "$baseUrl/24/thumbnail.jpg",
            images = listOf(
                "$baseUrl/24/1.jpg",
                "$baseUrl/24/2.jpg",
                "$baseUrl/24/3.jpg",
                "$baseUrl/24/4.jpg",
                "$baseUrl/24/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 25,
            title = "Gulab Powder 50 Gram",
            description = "Dry Rose Flower Powder Gulab Powder 50 Gram • Treats Wounds",
            price = 70.0,
            discountPercentage = 13.0,
            rating = 4.87,
            stock = 47,
            brand = "Dry Rose",
            category = "Groceries",
            thumbnail = "$baseUrl/25/thumbnail.jpg",
            images = listOf(
                "$baseUrl/25/1.png",
                "$baseUrl/25/2.jpg",
                "$baseUrl/25/3.png",
                "$baseUrl/25/4.jpg",
                "$baseUrl/25/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 26,
            title = "Plant Hanger For Home",
            description = "Boho Decor Plant Hanger For Home Wall Decoration Macrame Wall Hanging Shelf",
            price = 41.0,
            discountPercentage = 17.0,
            rating = 4.08,
            stock = 131,
            brand = "Boho Decor",
            category = "Home Decoration",
            thumbnail = "$baseUrl/26/thumbnail.jpg",
            images = listOf(
                "$baseUrl/26/1.jpg",
                "$baseUrl/26/2.jpg",
                "$baseUrl/26/3.jpg",
                "$baseUrl/26/4.jpg",
                "$baseUrl/26/5.jpg",
                "$baseUrl/26/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 27,
            title = "Flying Wooden Bird",
            description = "Package Include 6 Birds with Adhesive Tape Shape: 3D Shaped Wooden Birds Material: Wooden MDF, Laminated 3.5mm",
            price = 51.0,
            discountPercentage = 15.0,
            rating = 4.41,
            stock = 17,
            brand = "Flying Wooden",
            category = "Home Decoration",
            thumbnail = "$baseUrl/27/thumbnail.webp",
            images = listOf(
                "$baseUrl/27/1.jpg",
                "$baseUrl/27/2.jpg",
                "$baseUrl/27/3.jpg",
                "$baseUrl/27/4.jpg",
                "$baseUrl/27/thumbnail.webp"
            )
        ),
        ProductEntity(
            id = 28,
            title = "3D Embellishment Art Lamp",
            description = "3D led lamp sticker Wall sticker 3d wall art light on/off button  cell operated (included)",
            price = 20.0,
            discountPercentage = 16.0,
            rating = 4.82,
            stock = 54,
            brand = "LED Lights",
            category = "Home Decoration",
            thumbnail = "$baseUrl/28/thumbnail.jpg",
            images = listOf(
                "$baseUrl/28/1.jpg",
                "$baseUrl/28/2.jpg",
                "$baseUrl/28/3.png",
                "$baseUrl/28/4.jpg",
                "$baseUrl/28/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 29,
            title = "Handcraft Chinese style",
            description = "Handcraft Chinese style art luxury palace hotel villa mansion home decor ceramic vase with brass fruit plate",
            price = 60.0,
            discountPercentage = 15.0,
            rating = 4.44,
            stock = 7,
            brand = "luxury palace",
            category = "Home Decoration",
            thumbnail = "$baseUrl/29/thumbnail.webp",
            images = listOf(
                "$baseUrl/29/1.jpg",
                "$baseUrl/29/2.jpg",
                "$baseUrl/29/3.webp",
                "$baseUrl/29/4.webp",
                "$baseUrl/29/thumbnail.webp"
            )
        ),
        ProductEntity(
            id = 30,
            title = "Key Holder",
            description = "Attractive DesignMetallic materialFour key hooksReliable & DurablePremium Quality",
            price = 28.0,
            discountPercentage = 7.0,
            rating = 4.92,
            stock = 54,
            brand = "Golden",
            category = "Home Decoration",
            thumbnail = "$baseUrl/30/thumbnail.jpg",
            images = listOf(
                "$baseUrl/30/1.jpg",
                "$baseUrl/30/2.jpg",
                "$baseUrl/30/3.jpg",
                "$baseUrl/30/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 31,
            title = "Mornadi Velvet Bed",
            description = "Mornadi Velvet Bed Base with Headboard Slats Support Classic Style Bedroom Furniture Bed Set",
            price = 40.0,
            discountPercentage = 17.0,
            rating = 4.16,
            stock = 140,
            brand = "Furniture Bed Set",
            category = "Furniture",
            thumbnail = "$baseUrl/31/thumbnail.jpg",
            images = listOf(
                "$baseUrl/31/1.jpg",
                "$baseUrl/31/2.jpg",
                "$baseUrl/31/3.jpg",
                "$baseUrl/31/4.jpg",
                "$baseUrl/31/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 32,
            title = "Sofa for Coffe Cafe",
            description = "Ratttan Outdoor furniture Set Waterproof  Rattan Sofa for Coffe Cafe",
            price = 50.0,
            discountPercentage = 15.0,
            rating = 4.74,
            stock = 30,
            brand = "Ratttan Outdoor",
            category = "Furniture",
            thumbnail = "$baseUrl/32/thumbnail.jpg",
            images = listOf(
                "$baseUrl/32/1.jpg",
                "$baseUrl/32/2.jpg",
                "$baseUrl/32/3.jpg",
                "$baseUrl/32/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 33,
            title = "3 Tier Corner Shelves",
            description = "3 Tier Corner Shelves | 3 PCs Wall Mount Kitchen Shelf | Floating Bedroom Shelf",
            price = 700.0,
            discountPercentage = 17.0,
            rating = 4.31,
            stock = 106,
            brand = "Kitchen Shelf",
            category = "Furniture",
            thumbnail = "$baseUrl/33/thumbnail.jpg",
            images = listOf(
                "$baseUrl/33/1.jpg",
                "$baseUrl/33/2.jpg",
                "$baseUrl/33/3.jpg",
                "$baseUrl/33/4.jpg",
                "$baseUrl/33/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 34,
            title = "Plastic Table",
            description = "Very good quality plastic table for multi purpose now in reasonable price",
            price = 50.0,
            discountPercentage = 4.00,
            rating = 4.01,
            stock = 136,
            brand = "Multi Purpose",
            category = "Furniture",
            thumbnail = "$baseUrl/34/thumbnail.jpg",
            images = listOf(
                "$baseUrl/34/1.jpg",
                "$baseUrl/34/2.jpg",
                "$baseUrl/34/3.jpg",
                "$baseUrl/34/4.jpg",
                "$baseUrl/34/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 35,
            title = "3 DOOR PORTABLE",
            description = "Material: Stainless Steel and Fabric  Item Size: 110 cm x 45 cm x 175 cm Package Contents: 1 Storage Wardrobe",
            price = 41.0,
            discountPercentage = 7.0,
            rating = 4.06,
            stock = 68,
            brand = "AmnaMart",
            category = "Furniture",
            thumbnail = "$baseUrl/35/thumbnail.jpg",
            images = listOf(
                "$baseUrl/35/1.jpg",
                "$baseUrl/35/2.jpg",
                "$baseUrl/35/3.jpg",
                "$baseUrl/35/4.jpg",
                "$baseUrl/35/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 36,
            title = "Sleeve Shirt Women",
            description = "Cotton Solid Color Professional Wear Sleeve Shirt Women Work Blouses Wholesale Clothing Casual Plain Custom Top OEM Customized",
            price = 90.0,
            discountPercentage = 10.0,
            rating = 4.26,
            stock = 39,
            brand = "Professional Wear",
            category = "Tops",
            thumbnail = "$baseUrl/36/thumbnail.jpg",
            images = listOf(
                "$baseUrl/36/1.jpg",
                "$baseUrl/36/2.webp",
                "$baseUrl/36/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 37,
            title = "ank Tops for Women/Girls",
            description = "PACK OF 3 CAMISOLES ,VERY COMFORTABLE SOFT COTTON STUFF, COMFORTABLE IN ALL FOUR SEASONS",
            price = 50.0,
            discountPercentage = 12.0,
            rating = 4.52,
            stock = 107,
            brand = "Soft Cotton",
            category = "Tops",
            thumbnail = "$baseUrl/37/thumbnail.jpg",
            images = listOf(
                "$baseUrl/37/1.jpg",
                "$baseUrl/37/2.jpg",
                "$baseUrl/37/3.jpg",
                "$baseUrl/37/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 38,
            title = "sublimation plain kids tank",
            description = "sublimation plain kids tank tops wholesale",
            price = 100.0,
            discountPercentage = 11.0,
            rating = 4.8,
            stock = 20,
            brand = "Soft Cotton",
            category = "Tops",
            thumbnail = "$baseUrl/38/thumbnail.jpg",
            images = listOf(
                "$baseUrl/38/1.png",
                "$baseUrl/38/2.jpg",
                "$baseUrl/38/3.jpg",
                "$baseUrl/38/4.jpg"
            )
        ),
        ProductEntity(
            id = 39,
            title = "Women Sweater Wool",
            description = "2021 Custom Winter Fall Zebra Knit Crop Top Women Sweater Wool Mohair Cos Customize Crew Neck Women' S Crop Top Sweater",
            price = 600.0,
            discountPercentage = 17.0,
            rating = 4.55,
            stock = 55,
            brand = "Top Sweater",
            category = "Tops",
            thumbnail = "$baseUrl/39/thumbnail.jpg",
            images = listOf(
                "$baseUrl/39/1.jpg"
            )
        ),
        ProductEntity(
            id = 40,
            title = "women winter clothes",
            description = "women winter clothes thick fleece hoodie top with sweat pantjogger women sweatsuit set joggers pants two piece pants set",
            price = 57.0,
            discountPercentage = 13.0,
            rating = 4.91,
            stock = 84,
            brand = "Top Sweater",
            category = "Tops",
            thumbnail = "$baseUrl/40/thumbnail.jpg",
            images = listOf(
                "$baseUrl/40/1.jpg",
                "$baseUrl/40/2.jpg"
            )
        ),
        ProductEntity(
            id = 41,
            title = "NIGHT SUIT",
            description = "NIGHT SUIT RED MICKY MOUSE..  For Girls. Fantastic Suits.",
            price = 55.0,
            discountPercentage = 15.0,
            rating = 4.65,
            stock = 21,
            brand = "RED MICKY MOUSE..",
            category = "Women Dresses",
            thumbnail = "$baseUrl/41/thumbnail.webp",
            images = listOf(
                "$baseUrl/41/1.webp",
                "$baseUrl/41/2.jpg",
                "$baseUrl/41/3.jpg"
            )
        ),
        ProductEntity(
            id = 42,
            title = "Stiched Kurta plus trouser",
            description = "FABRIC: LILEIN CHEST: 21 LENGHT: 37 TROUSER: (38) :ARABIC LILEIN",
            price = 80.0,
            discountPercentage = 15.0,
            rating = 4.05,
            stock = 148,
            brand = "Digital Printed",
            category = "Women Dresses",
            thumbnail = "$baseUrl/42/thumbnail.jpg",
            images = listOf(
                "$baseUrl/42/1.png",
                "$baseUrl/42/2.png",
                "$baseUrl/42/3.png",
                "$baseUrl/42/4.jpg",
                "$baseUrl/42/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 43,
            title = "frock gold printed",
            description = "Ghazi fabric long frock gold printed ready to wear stitched collection (G992)",
            price = 600.0,
            discountPercentage = 15.0,
            rating = 4.31,
            stock = 150,
            brand = "Ghazi Fabric",
            category = "Women Dresses",
            thumbnail = "$baseUrl/43/thumbnail.jpg",
            images = listOf(
                "$baseUrl/43/1.jpg",
                "$baseUrl/43/2.jpg"
            )
        ),
        ProductEntity(
            id = 44,
            title = "Ladies Multicolored Dress",
            description = "This classy shirt for women gives you a gorgeous look on everyday wear and specially for semi-casual wears.",
            price = 79.0,
            discountPercentage = 16.0,
            rating = 4.03,
            stock = 2,
            brand = "Ghazi Fabric",
            category = "Women Dresses",
            thumbnail = "$baseUrl/44/thumbnail.jpg",
            images = listOf(
                "$baseUrl/44/1.jpg",
                "$baseUrl/44/2.jpg",
                "$baseUrl/44/3.jpg",
                "$baseUrl/44/4.jpg",
                "$baseUrl/44/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 45,
            title = "Malai Maxi Dress",
            description = "Ready to wear, Unique design according to modern standard fashion, Best fitting ,Imported stuff",
            price = 50.0,
            discountPercentage = 5.0,
            rating = 4.67,
            stock = 96,
            brand = "IELGY",
            category = "Women Dresses",
            thumbnail = "$baseUrl/45/thumbnail.jpg",
            images = listOf(
                "$baseUrl/45/1.jpg"
            )
        ),
        ProductEntity(
            id = 46,
            title = "women's shoes",
            description = "Close: Lace, Style with bottom: Increased inside, Sole Material: Rubber",
            price = 40.0,
            discountPercentage = 16.0,
            rating = 4.14,
            stock = 72,
            brand = "IELGY fashion",
            category = "Women Shoes",
            thumbnail = "$baseUrl/46/thumbnail.jpg",
            images = listOf(
                "$baseUrl/46/1.webp",
                "$baseUrl/46/2.jpg",
                "$baseUrl/46/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 47,
            title = "Sneaker shoes",
            description = "Synthetic Leather Casual Sneaker shoes for Women/girls Sneakers For Women",
            price = 120.0,
            discountPercentage = 10.0,
            rating = 4.19,
            stock = 50,
            brand = "Synthetic Leather",
            category = "Women Shoes",
            thumbnail = "$baseUrl/47/thumbnail.jpeg",
            images = listOf(
                "$baseUrl/47/1.jpg",
                "$baseUrl/47/thumbnail.jpeg"
            )
        ),
        ProductEntity(
            id = 48,
            title = "Women Strip Heel",
            description = "Features: Flip-flops, Mid Heel, Comfortable, Striped Heel, Antiskid, Striped",
            price = 40.0,
            discountPercentage = 10.0,
            rating = 4.02,
            stock = 25,
            brand = "Sandals Flip Flops",
            category = "Women Shoes",
            thumbnail = "$baseUrl/48/thumbnail.png",
            images = listOf(
                "$baseUrl/48/1.png"
            )
        ),
        ProductEntity(
            id = 49,
            title = "Chappals & Shoe Ladies Metallic",
            description = "Women Chappals & Shoe Ladies Metallic Tong Thong Sandal Flat Summer 2020 Maasai Sandals",
            price = 23.0,
            discountPercentage = 2.0,
            rating = 4.72,
            stock = 107,
            brand = "Maasai Sandals",
            category = "Women Shoes",
            thumbnail = "$baseUrl/49/thumbnail.jpg",
            images = listOf(
                "$baseUrl/49/1.jpg",
                "$baseUrl/49/2.jpg",
                "$baseUrl/49/3.webp",
                "$baseUrl/49/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 50,
            title = "Women Shoes",
            description = "2020 New Arrivals Genuine Leather Fashion Trend Platform Summer Women Shoes",
            price = 36.0,
            discountPercentage = 16.0,
            rating = 4.33,
            stock = 46,
            brand = "Arrivals Genuine",
            category = "Women Shoes",
            thumbnail = "$baseUrl/50/thumbnail.jpg",
            images = listOf(
                "$baseUrl/50/1.jpg"
            )
        ),
        ProductEntity(
            id = 51,
            title = "half sleeves T shirts",
            description = "Many store is creating new designs and trend every month and every year. Daraz.pk have a beautiful range of men fashion brands",
            price = 23.0,
            discountPercentage = 12.0,
            rating = 4.26,
            stock = 132,
            brand = "Vintage Apparel",
            category = "Men Shirts",
            thumbnail = "$baseUrl/51/thumbnail.jpg",
            images = listOf(
                "$baseUrl/51/1.png",
                "$baseUrl/51/2.jpg",
                "$baseUrl/51/3.jpg",
                "$baseUrl/51/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 52,
            title = "FREE FIRE T Shirt",
            description = "quality and professional print - It doesn't just look high quality, it is high quality.",
            price = 10.0,
            discountPercentage = 14.0,
            rating = 4.52,
            stock = 128,
            brand = "FREE FIRE",
            category = "Men Shirts",
            thumbnail = "$baseUrl/52/thumbnail.png",
            images = listOf(
                "$baseUrl/52/1.png",
                "$baseUrl/52/2.png",
                "$baseUrl/52/3.jpg",
                "$baseUrl/52/4.jpg"
            )
        ),
        ProductEntity(
            id = 53,
            title = "printed high quality T shirts",
            description = "Brand: vintage Apparel ,Export quality",
            price = 35.0,
            discountPercentage = 7.0,
            rating = 4.89,
            stock = 6,
            brand = "Vintage Apparel",
            category = "Men Shirts",
            thumbnail = "$baseUrl/53/thumbnail.jpg",
            images = listOf(
                "$baseUrl/53/1.webp",
                "$baseUrl/53/2.jpg",
                "$baseUrl/53/3.jpg",
                "$baseUrl/53/4.jpg",
                "$baseUrl/53/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 54,
            title = "Pubg Printed Graphic T-Shirt",
            description = "Product Description Features: 100% Ultra soft Polyester Jersey. Vibrant & colorful printing on front. Feels soft as cotton without ever cracking",
            price = 46.0,
            discountPercentage = 16.0,
            rating = 4.62,
            stock = 136,
            brand = "The Warehouse",
            category = "Men Shirts",
            thumbnail = "$baseUrl/54/thumbnail.jpg",
            images = listOf(
                "$baseUrl/54/1.jpg",
                "$baseUrl/54/2.jpg",
                "$baseUrl/54/3.jpg",
                "$baseUrl/54/4.jpg",
                "$baseUrl/54/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 55,
            title = "Money Heist Printed Summer T Shirts",
            description = "Fabric Jercy, Size: M & L Wear Stylish Dual Stiched",
            price = 66.0,
            discountPercentage = 15.0,
            rating = 4.9,
            stock = 122,
            brand = "The Warehouse",
            category = "Men Shirts",
            thumbnail = "$baseUrl/55/thumbnail.jpg",
            images = listOf(
                "$baseUrl/55/1.jpg",
                "$baseUrl/55/2.webp",
                "$baseUrl/55/3.jpg",
                "$baseUrl/55/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 56,
            title = "Sneakers Joggers Shoes",
            description = "Gender: Men , Colors: Same as DisplayedCondition: 100% Brand New",
            price = 40.0,
            discountPercentage = 12.0,
            rating = 4.38,
            stock = 6,
            brand = "Sneakers",
            category = "Men Shoes",
            thumbnail = "$baseUrl/56/thumbnail.jpg",
            images = listOf(
                "$baseUrl/56/1.jpg",
                "$baseUrl/56/2.jpg",
                "$baseUrl/56/3.jpg",
                "$baseUrl/56/4.jpg",
                "$baseUrl/56/5.jpg",
                "$baseUrl/56/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 57,
            title = "Loafers for men",
            description = "Men Shoes - Loafers for men - Rubber Shoes - Nylon Shoes - Shoes for men - Moccassion - Pure Nylon (Rubber) Expot Quality.",
            price = 47.0,
            discountPercentage = 10.0,
            rating = 4.91,
            stock = 20,
            brand = "Rubber",
            category = "Men Shoes",
            thumbnail = "$baseUrl/57/thumbnail.jpg",
            images = listOf(
                "$baseUrl/57/1.jpg",
                "$baseUrl/57/2.jpg",
                "$baseUrl/57/3.jpg",
                "$baseUrl/57/4.jpg",
                "$baseUrl/57/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 58,
            title = "formal offices shoes",
            description = "Pattern Type: Solid, Material: PU, Toe Shape: Pointed Toe ,Outsole Material: Rubber",
            price = 57.0,
            discountPercentage = 12.0,
            rating = 4.41,
            stock = 68,
            brand = "The Warehouse",
            category = "Men Shoes",
            thumbnail = "$baseUrl/58/thumbnail.jpg",
            images = listOf(
                "$baseUrl/58/1.jpg",
                "$baseUrl/58/2.jpg",
                "$baseUrl/58/3.jpg",
                "$baseUrl/58/4.jpg",
                "$baseUrl/58/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 59,
            title = "Spring and summer shoes",
            description = "Comfortable stretch cloth, lightweight body; ,rubber sole, anti-skid wear;",
            price = 20.0,
            discountPercentage = 8.0,
            rating = 4.33,
            stock = 137,
            brand = "Sneakers",
            category = "Men Shoes",
            thumbnail = "$baseUrl/59/thumbnail.jpg",
            images = listOf(
                "$baseUrl/59/1.jpg",
                "$baseUrl/59/2.jpg",
                "$baseUrl/59/3.jpg",
                "$baseUrl/59/4.jpg",
                "$baseUrl/59/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 60,
            title = "Stylish Casual Jeans Shoes",
            description = "High Quality ,Stylish design ,Comfortable wear ,FAshion ,Durable",
            price = 58.0,
            discountPercentage = 7.0,
            rating = 4.55,
            stock = 129,
            brand = "Sneakers",
            category = "Men Shoes",
            thumbnail = "$baseUrl/60/thumbnail.jpg",
            images = listOf(
                "$baseUrl/60/1.jpg",
                "$baseUrl/60/2.jpg",
                "$baseUrl/60/3.jpg",
                "$baseUrl/60/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 61,
            title = "Leather Straps Wristwatch",
            description = "Style:Sport ,Clasp:Buckles ,Water Resistance Depth:3Bar",
            price = 120.0,
            discountPercentage = 7.0,
            rating = 4.63,
            stock = 91,
            brand = "Naviforce",
            category = "Men Watches",
            thumbnail = "$baseUrl/61/thumbnail.jpg",
            images = listOf(
                "$baseUrl/61/1.jpg",
                "$baseUrl/61/2.png",
                "$baseUrl/61/3.jpg"
            )
        ),
        ProductEntity(
            id = 62,
            title = "Waterproof Leather Brand Watch",
            description = "Watch Crown With Environmental IPS Bronze Electroplating; Display system of 12 hours",
            price = 46.0,
            discountPercentage = 3.0,
            rating = 4.05,
            stock = 95,
            brand = "SKMEI 9117",
            category = "Men Watches",
            thumbnail = "$baseUrl/62/thumbnail.jpg",
            images = listOf(
                "$baseUrl/62/1.jpg",
                "$baseUrl/62/2.jpg"
            )
        ),
        ProductEntity(
            id = 63,
            title = "Royal Blue Premium Watch",
            description = "Men Silver Chain Royal Blue Premium Watch Latest Analog Watch",
            price = 50.0,
            discountPercentage = 2.0,
            rating = 4.89,
            stock = 142,
            brand = "SKMEI 9117",
            category = "Men Watches",
            thumbnail = "$baseUrl/63/thumbnail.webp",
            images = listOf(
                "$baseUrl/63/1.jpg",
                "$baseUrl/63/2.jpg",
                "$baseUrl/63/3.png",
                "$baseUrl/63/4.jpeg"
            )
        ),
        ProductEntity(
            id = 64,
            title = "Leather Strap Skeleton Watch",
            description = "Leather Strap Skeleton Watch for Men - Stylish and Latest Design",
            price = 46.0,
            discountPercentage = 10.0,
            rating = 4.98,
            stock = 61,
            brand = "Strap Skeleton",
            category = "Men Watches",
            thumbnail = "$baseUrl/64/thumbnail.jpg",
            images = listOf(
                "$baseUrl/64/1.jpg",
                "$baseUrl/64/2.webp",
                "$baseUrl/64/3.jpg",
                "$baseUrl/64/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 65,
            title = "Stainless Steel Wrist Watch",
            description = "Stylish Watch For Man (Luxury) Classy Men's Stainless Steel Wrist Watch - Box Packed",
            price = 47.0,
            discountPercentage = 17.0,
            rating = 4.79,
            stock = 94,
            brand = "Stainless",
            category = "Men Watches",
            thumbnail = "$baseUrl/65/thumbnail.webp",
            images = listOf(
                "$baseUrl/65/1.jpg",
                "$baseUrl/65/2.webp",
                "$baseUrl/65/3.jpg",
                "$baseUrl/65/4.webp",
                "$baseUrl/65/thumbnail.webp"
            )
        ),
        ProductEntity(
            id = 66,
            title = "Steel Analog Couple Watches",
            description = "Elegant design, Stylish ,Unique & Trendy,Comfortable wear",
            price = 35.0,
            discountPercentage = 3.0,
            rating = 4.79,
            stock = 24,
            brand = "Eastern Watches",
            category = "Women Watches",
            thumbnail = "$baseUrl/66/thumbnail.jpg",
            images = listOf(
                "$baseUrl/66/1.jpg",
                "$baseUrl/66/2.jpg",
                "$baseUrl/66/3.jpg",
                "$baseUrl/66/4.JPG",
                "$baseUrl/66/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 67,
            title = "Fashion Magnetic Wrist Watch",
            description = "Buy this awesome  The product is originally manufactured by the company and it's a top selling product with a very reasonable",
            price = 60.0,
            discountPercentage = 16.0,
            rating = 4.03,
            stock = 46,
            brand = "Eastern Watches",
            category = "Women Watches",
            thumbnail = "$baseUrl/67/thumbnail.jpg",
            images = listOf(
                "$baseUrl/67/1.jpg",
                "$baseUrl/67/2.jpg"
            )
        ),
        ProductEntity(
            id = 68,
            title = "Stylish Luxury Digital Watch",
            description = "Stylish Luxury Digital Watch For Girls / Women - Led Smart Ladies Watches For Girls",
            price = 57.0,
            discountPercentage = 9.0,
            rating = 4.55,
            stock = 77,
            brand = "Luxury Digital",
            category = "Women Watches",
            thumbnail = "$baseUrl/68/thumbnail.webp",
            images = listOf(
                "$baseUrl/68/1.jpg",
                "$baseUrl/68/2.jpg"
            )
        ),
        ProductEntity(
            id = 69,
            title = "Golden Watch Pearls Bracelet Watch",
            description = "Product details of Golden Watch Pearls Bracelet Watch For Girls - Golden Chain Ladies Bracelate Watch for Women",
            price = 47.0,
            discountPercentage = 17.0,
            rating = 4.77,
            stock = 89,
            brand = "Watch Pearls",
            category = "Women Watches",
            thumbnail = "$baseUrl/69/thumbnail.jpg",
            images = listOf(
                "$baseUrl/69/1.jpg",
                "$baseUrl/69/2.jpg"
            )
        ),
        ProductEntity(
            id = 70,
            title = "Stainless Steel Women",
            description = "Fashion Skmei 1830 Shell Dial Stainless Steel Women Wrist Watch Lady Bracelet Watch Quartz Watches Ladies",
            price = 35.0,
            discountPercentage = 8.0,
            rating = 4.08,
            stock = 111,
            brand = "Bracelet",
            category = "Women Watches",
            thumbnail = "$baseUrl/70/thumbnail.jpg",
            images = listOf(
                "$baseUrl/70/1.jpg",
                "$baseUrl/70/2.jpg",
                "$baseUrl/70/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 71,
            title = "Women Shoulder Bags",
            description = "LouisWill Women Shoulder Bags Long Clutches Cross Body Bags Phone Bags PU Leather Hand Bags Large Capacity Card Holders Zipper Coin Purses Fashion Crossbody Bags for Girls Ladies",
            price = 46.0,
            discountPercentage = 14.0,
            rating = 4.71,
            stock = 17,
            brand = "LouisWill",
            category = "Women Bags",
            thumbnail = "$baseUrl/71/thumbnail.jpg",
            images = listOf(
                "$baseUrl/71/1.jpg",
                "$baseUrl/71/2.jpg",
                "$baseUrl/71/3.webp",
                "$baseUrl/71/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 72,
            title = "Handbag For Girls",
            description = "This fashion is designed to add a charming effect to your casual outfit. This Bag is made of synthetic leather.",
            price = 23.0,
            discountPercentage = 17.0,
            rating = 4.91,
            stock = 27,
            brand = "LouisWill",
            category = "Women Bags",
            thumbnail = "$baseUrl/72/thumbnail.webp",
            images = listOf(
                "$baseUrl/72/1.jpg",
                "$baseUrl/72/2.png",
                "$baseUrl/72/3.webp",
                "$baseUrl/72/4.jpg",
                "$baseUrl/72/thumbnail.webp"
            )
        ),
        ProductEntity(
            id = 73,
            title = "Fancy hand clutch",
            description = "This fashion is designed to add a charming effect to your casual outfit. This Bag is made of synthetic leather.",
            price = 44.0,
            discountPercentage = 10.0,
            rating = 4.18,
            stock = 101,
            brand = "Bracelet",
            category = "Women Bags",
            thumbnail = "$baseUrl/73/thumbnail.jpg",
            images = listOf(
                "$baseUrl/73/1.jpg",
                "$baseUrl/73/2.webp",
                "$baseUrl/73/3.jpg",
                "$baseUrl/73/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 74,
            title = "Leather Hand Bag",
            description = "It features an attractive design that makes it a must have accessory in your collection. We sell different kind of bags for boys, kids, women, girls and also for unisex.",
            price = 57.0,
            discountPercentage = 11.0,
            rating = 4.01,
            stock = 43,
            brand = "Copenhagen Luxe",
            category = "Women Bags",
            thumbnail = "$baseUrl/74/thumbnail.jpg",
            images = listOf(
                "$baseUrl/74/1.jpg",
                "$baseUrl/74/2.jpg",
                "$baseUrl/74/3.jpg",
                "$baseUrl/74/4.jpg",
                "$baseUrl/74/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 75,
            title = "Seven Pocket Women Bag",
            description = "Seven Pocket Women Bag Handbags Lady Shoulder Crossbody Bag Female Purse Seven Pocket Bag",
            price = 68.0,
            discountPercentage = 14.0,
            rating = 4.93,
            stock = 13,
            brand = "Steal Frame",
            category = "Women Bags",
            thumbnail = "$baseUrl/75/thumbnail.jpg",
            images = listOf(
                "$baseUrl/75/1.jpg",
                "$baseUrl/75/2.jpg",
                "$baseUrl/75/3.jpg",
                "$baseUrl/75/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 76,
            title = "Silver Ring Set Women",
            description = "Jewelry Type:RingsCertificate Type:NonePlating:Silver PlatedShapeattern:noneStyle:CLASSICReligious",
            price = 70.0,
            discountPercentage = 13.0,
            rating = 4.61,
            stock = 51,
            brand = "Darojay",
            category = "Women Jewellery",
            thumbnail = "$baseUrl/76/thumbnail.jpg",
            images = listOf(
                "$baseUrl/76/1.jpg",
                "$baseUrl/76/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 77,
            title = "Rose Ring",
            description = "Brand: The Greetings Flower Colour: RedRing Colour: GoldenSize: Adjustable",
            price = 100.0,
            discountPercentage = 3.0,
            rating = 4.21,
            stock = 149,
            brand = "Copenhagen Luxe",
            category = "Women Jewellery",
            thumbnail = "$baseUrl/77/thumbnail.jpg",
            images = listOf(
                "$baseUrl/77/1.jpg",
                "$baseUrl/77/2.jpg",
                "$baseUrl/77/3.jpg",
                "$baseUrl/77/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 78,
            title = "Rhinestone Korean Style Open Rings",
            description = "Fashion Jewellery 3Pcs Adjustable Pearl Rhinestone Korean Style Open Rings For Women",
            price = 30.0,
            discountPercentage = 8.0,
            rating = 4.69,
            stock = 9,
            brand = "Fashion Jewellery",
            category = "Women Jewellery",
            thumbnail = "$baseUrl/78/thumbnail.jpg",
            images = listOf("$baseUrl/78/thumbnail.jpg")
        ),
        ProductEntity(
            id = 79,
            title = "Elegant Female Pearl Earrings",
            description = "Elegant Female Pearl Earrings Set Zircon Pearl Earings Women Party Accessories 9 Pairs/Set",
            price = 30.0,
            discountPercentage = 12.0,
            rating = 4.74,
            stock = 16,
            brand = "Fashion Jewellery",
            category = "Women Jewellery",
            thumbnail = "$baseUrl/79/thumbnail.jpg",
            images = listOf("$baseUrl/79/1.jpg")
        ),
        ProductEntity(
            id = 80,
            title = "Chain Pin Tassel Earrings",
            description = "Pair Of Ear Cuff Butterfly Long Chain Pin Tassel Earrings - Silver ( Long Life Quality Product)",
            price = 45.0,
            discountPercentage = 17.0,
            rating = 4.59,
            stock = 9,
            brand = "Cuff Butterfly",
            category = "Women Jewellery",
            thumbnail = "$baseUrl/80/thumbnail.jpg",
            images = listOf(
                "$baseUrl/80/1.jpg",
                "$baseUrl/80/2.jpg",
                "$baseUrl/80/3.jpg",
                "$baseUrl/80/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 81,
            title = "Round Silver Frame Sunglasses",
            description = "A pair of sunglasses can protect your eyes from being hurt. For car driving, vacation travel, outdoor activities, social gatherings,",
            price = 19.0,
            discountPercentage = 10.0,
            rating = 4.94,
            stock = 78,
            brand = "Designer Sunglasses",
            category = "Sunglasses",
            thumbnail = "$baseUrl/81/thumbnail.jpg",
            images = listOf(
                "$baseUrl/81/1.jpg",
                "$baseUrl/81/2.jpg",
                "$baseUrl/81/3.jpg",
                "$baseUrl/81/4.webp",
                "$baseUrl/81/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 82,
            title = "Kabir Singh Square Sunglasses",
            description = "Orignal Metal Kabir Singh design 2020 Sunglasses Men Brand Designer Sunglasses Kabir Singh Square Sunglass",
            price = 50.0,
            discountPercentage = 15.0,
            rating = 4.62,
            stock = 78,
            brand = "Designer Sunglasses",
            category = "Sunglasses",
            thumbnail = "$baseUrl/82/thumbnail.jpg",
            images = listOf(
                "$baseUrl/82/1.jpg",
                "$baseUrl/82/2.webp",
                "$baseUrl/82/3.jpg",
                "$baseUrl/82/4.jpg",
                "$baseUrl/82/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 83,
            title = "Wiley X Night Vision Yellow Glasses",
            description = "Wiley X Night Vision Yellow Glasses for Riders - Night Vision Anti Fog Driving Glasses - Free Night Glass Cover - Shield Eyes From Dust and Virus- For Night Sport Matches",
            price = 30.0,
            discountPercentage = 6.0,
            rating = 4.97,
            stock = 115,
            brand = "mastar watch",
            category = "Sunglasses",
            thumbnail = "$baseUrl/83/thumbnail.jpg",
            images = listOf(
                "$baseUrl/83/1.jpg",
                "$baseUrl/83/2.jpg",
                "$baseUrl/83/3.jpg",
                "$baseUrl/83/4.jpg",
                "$baseUrl/83/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 84,
            title = "Square Sunglasses",
            description = "Fashion Oversized Square Sunglasses Retro Gradient Big Frame Sunglasses For Women One Piece Gafas Shade Mirror Clear Lens 17059",
            price = 28.0,
            discountPercentage = 13.0,
            rating = 4.64,
            stock = 64,
            brand = "mastar watch",
            category = "Sunglasses",
            thumbnail = "$baseUrl/84/thumbnail.jpg",
            images = listOf(
                "$baseUrl/84/1.jpg",
                "$baseUrl/84/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 85,
            title = "LouisWill Men Sunglasses",
            description = "LouisWill Men Sunglasses Polarized Sunglasses UV400 Sunglasses Day Night Dual Use Safety Driving Night Vision Eyewear AL-MG Frame Sunglasses with Free Box for Drivers",
            price = 50.0,
            discountPercentage = 11.0,
            rating = 4.98,
            stock = 92,
            brand = "LouisWill",
            category = "Sunglasses",
            thumbnail = "$baseUrl/85/thumbnail.jpg",
            images = listOf(
                "$baseUrl/85/1.jpg",
                "$baseUrl/85/2.jpg",
                "$baseUrl/85/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 86,
            title = "Bluetooth Aux",
            description = "Bluetooth Aux Bluetooth Car Aux Car Bluetooth Transmitter Aux Audio Receiver Handfree Car Bluetooth Music Receiver Universal 3.5mm Streaming A2DP Wireless Auto AUX Audio Adapter With Mic For Phone MP3",
            price = 25.0,
            discountPercentage = 10.0,
            rating = 4.57,
            stock = 22,
            brand = "Car Aux",
            category = "Automotive",
            thumbnail = "$baseUrl/86/thumbnail.jpg",
            images = listOf(
                "$baseUrl/86/1.jpg",
                "$baseUrl/86/2.webp",
                "$baseUrl/86/3.jpg",
                "$baseUrl/86/4.jpg",
                "$baseUrl/86/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 87,
            title = "t Temperature Controller Incubator Controller",
            description = "Both Heat and Cool Purpose, Temperature control range; -50 to +110, Temperature measurement accuracy; 0.1, Control accuracy; 0.1",
            price = 40.0,
            discountPercentage = 11.0,
            rating = 4.54,
            stock = 37,
            brand = "W1209 DC12V",
            category = "Automotive",
            thumbnail = "$baseUrl/87/thumbnail.jpg",
            images = listOf(
                "$baseUrl/87/1.jpg",
                "$baseUrl/87/2.jpg",
                "$baseUrl/87/3.jpg",
                "$baseUrl/87/4.jpg",
                "$baseUrl/87/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 88,
            title = "TC Reusable Silicone Magic Washing Gloves",
            description = "TC Reusable Silicone Magic Washing Gloves with Scrubber, Cleaning Brush Scrubber Gloves Heat Resistant Pair for Cleaning of Kitchen, Dishes, Vegetables and Fruits, Bathroom, Car Wash, Pet Care and Multipurpose",
            price = 29.0,
            discountPercentage = 3.0,
            rating = 4.98,
            stock = 42,
            brand = "TC Reusable",
            category = "Automotive",
            thumbnail = "$baseUrl/88/thumbnail.jpg",
            images = listOf(
                "$baseUrl/88/1.jpg",
                "$baseUrl/88/2.jpg",
                "$baseUrl/88/3.jpg",
                "$baseUrl/88/4.webp",
                "$baseUrl/88/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 89,
            title = "Qualcomm original Car Charger",
            description = "best Quality CHarger , Highly Recommended to all best Quality CHarger , Highly Recommended to all",
            price = 40.0,
            discountPercentage = 17.0,
            rating = 4.2,
            stock = 79,
            brand = "TC Reusable",
            category = "Automotive",
            thumbnail = "$baseUrl/89/thumbnail.jpg",
            images = listOf(
                "$baseUrl/89/1.jpg",
                "$baseUrl/89/2.jpg",
                "$baseUrl/89/3.jpg",
                "$baseUrl/89/4.jpg",
                "$baseUrl/89/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 90,
            title = "Cycle Bike Glow",
            description = "Universal fitment and easy to install no special wires, can be easily installed and removed. Fits most standard tyre air stem valves of road, mountain bicycles, motocycles and cars.Bright led will turn on w",
            price = 35.0,
            discountPercentage = 11.0,
            rating = 4.1,
            stock = 63,
            brand = "Neon LED Light",
            category = "Automotive",
            thumbnail = "$baseUrl/90/thumbnail.jpg",
            images = listOf(
                "$baseUrl/90/1.jpg",
                "$baseUrl/90/2.jpg",
                "$baseUrl/90/3.jpg",
                "$baseUrl/90/4.jpg",
                "$baseUrl/90/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 91,
            title = "Black Motorbike",
            description = "Engine Type:Wet sump, Single Cylinder, Four Stroke, Two Valves, Air Cooled with SOHC (Single Over Head Cam) Chain Drive Bore & Stroke:47.0 x 49.5 MM",
            price = 569.0,
            discountPercentage = 13.0,
            rating = 4.04,
            stock = 115,
            brand = "METRO 70cc Motorcycle - MR70",
            category = "Motorcycle",
            thumbnail = "$baseUrl/91/thumbnail.jpg",
            images = listOf(
                "$baseUrl/91/1.jpg",
                "$baseUrl/91/2.jpg",
                "$baseUrl/91/3.jpg",
                "$baseUrl/91/4.jpg",
                "$baseUrl/91/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 92,
            title = "HOT SALE IN EUROPE electric racing motorcycle",
            description = "HOT SALE IN EUROPE electric racing motorcycle electric motorcycle for sale adult electric motorcycles",
            price = 920.0,
            discountPercentage = 14.0,
            rating = 4.19,
            stock = 22,
            brand = "BRAVE BULL",
            category = "Motorcycle",
            thumbnail = "$baseUrl/92/thumbnail.jpg",
            images = listOf(
                "$baseUrl/92/1.jpg",
                "$baseUrl/92/2.jpg",
                "$baseUrl/92/3.jpg",
                "$baseUrl/92/4.jpg"
            )
        ),
        ProductEntity(
            id = 93,
            title = "Automatic Motor Gas Motorcycles",
            description = "150cc 4-Stroke Motorcycle Automatic Motor Gas Motorcycles Scooter motorcycles 150cc scooter",
            price = 1050.0,
            discountPercentage = 3.0,
            rating = 4.84,
            stock = 127,
            brand = "shock absorber",
            category = "Motorcycle",
            thumbnail = "$baseUrl/93/thumbnail.jpg",
            images = listOf(
                "$baseUrl/93/1.jpg",
                "$baseUrl/93/2.jpg",
                "$baseUrl/93/3.jpg",
                "$baseUrl/93/4.jpg",
                "$baseUrl/93/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 94,
            title = "new arrivals Fashion motocross goggles",
            description = "new arrivals Fashion motocross goggles motorcycle motocross racing motorcycle",
            price = 900.0,
            discountPercentage = 3.0,
            rating = 4.06,
            stock = 109,
            brand = "JIEPOLLY",
            category = "Motorcycle",
            thumbnail = "$baseUrl/94/thumbnail.webp",
            images = listOf(
                "$baseUrl/94/1.webp",
                "$baseUrl/94/2.jpg",
                "$baseUrl/94/3.jpg",
                "$baseUrl/94/thumbnail.webp"
            )
        ),
        ProductEntity(
            id = 95,
            title = "Wholesale cargo lashing Belt",
            description = "Wholesale cargo lashing Belt Tie Down end Ratchet strap customized strap 25mm motorcycle 1500kgs with rubber handle",
            price = 930.0,
            discountPercentage = 17.0,
            rating = 4.21,
            stock = 144,
            brand = "Xiangle",
            category = "Motorcycle",
            thumbnail = "$baseUrl/95/thumbnail.jpg",
            images = listOf(
                "$baseUrl/95/1.jpg",
                "$baseUrl/95/2.jpg",
                "$baseUrl/95/3.jpg",
                "$baseUrl/95/4.jpg",
                "$baseUrl/95/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 96,
            title = "lighting ceiling kitchen",
            description = "Wholesale slim hanging decorative kid room lighting ceiling kitchen chandeliers pendant light modern",
            price = 30.0,
            discountPercentage = 14.0,
            rating = 4.83,
            stock = 96,
            brand = "lightingbrilliance",
            category = "Lighting",
            thumbnail = "$baseUrl/96/thumbnail.jpg",
            images = listOf(
                "$baseUrl/96/1.jpg",
                "$baseUrl/96/2.jpg",
                "$baseUrl/96/3.jpg",
                "$baseUrl/96/4.jpg"
            )
        ),
        ProductEntity(
            id = 97,
            title = "Metal Ceramic Flower",
            description = "Metal Ceramic Flower Chandelier Home Lighting American Vintage Hanging Lighting Pendant Lamp",
            price = 35.0,
            discountPercentage = 10.0,
            rating = 4.93,
            stock = 146,
            brand = "Ifei Home",
            category = "Lighting",
            thumbnail = "$baseUrl/97/thumbnail.jpg",
            images = listOf(
                "$baseUrl/97/1.jpg",
                "$baseUrl/97/2.jpg",
                "$baseUrl/97/3.jpg",
                "$baseUrl/97/4.webp",
                "$baseUrl/97/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 98,
            title = "3 lights lndenpant kitchen islang",
            description = "3 lights lndenpant kitchen islang dining room pendant rice paper chandelier contemporary led pendant light modern chandelier",
            price = 34.0,
            discountPercentage = 5.0,
            rating = 4.99,
            stock = 44,
            brand = "DADAWU",
            category = "Lighting",
            thumbnail = "$baseUrl/98/thumbnail.jpg",
            images = listOf(
                "$baseUrl/98/1.jpg",
                "$baseUrl/98/2.jpg",
                "$baseUrl/98/3.jpg",
                "$baseUrl/98/4.jpg",
                "$baseUrl/98/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 99,
            title = "American Vintage Wood Pendant Light",
            description = "American Vintage Wood Pendant Light Farmhouse Antique Hanging Lamp Lampara Colgante",
            price = 46.0,
            discountPercentage = 8.0,
            rating = 4.32,
            stock = 138,
            brand = "Ifei Home",
            category = "Lighting",
            thumbnail = "$baseUrl/99/thumbnail.jpg",
            images = listOf(
                "$baseUrl/99/1.jpg",
                "$baseUrl/99/2.jpg",
                "$baseUrl/99/3.jpg",
                "$baseUrl/99/4.jpg",
                "$baseUrl/99/thumbnail.jpg"
            )
        ),
        ProductEntity(
            id = 100,
            title = "Crystal chandelier maria theresa for 12 light",
            description = "Crystal chandelier maria theresa for 12 light",
            price = 47.0,
            discountPercentage = 16.0,
            rating = 4.74,
            stock = 133,
            brand = "YIOSI",
            category = "Lighting",
            thumbnail = "$baseUrl/100/thumbnail.jpg",
            images = listOf(
                "$baseUrl/100/1.jpg",
                "$baseUrl/100/2.jpg",
                "$baseUrl/100/3.jpg",
                "$baseUrl/100/thumbnail.jpg"
            )
        )
    )
}
