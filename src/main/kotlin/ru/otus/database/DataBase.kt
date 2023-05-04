package ru.otus.database

import ru.otus.OtusKitchenApp
import ru.otus.data.*
import ru.otus.data.model.*
import ru.otus.data.model.MenuItem.MenuItemFoodValue
import ru.otus.data.model.MenuItem.MenuSubItem
import ru.otus.data.model.OrderItem.OrderPosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class DataBase : AddressRepository, BalanceRepository, MenuRepository, OrderRepository, SaleRepository, AuthRepository {
    private var validKey = ValidateKey(generateKey(), true)

    private val addressList = mutableListOf<AddressItem>()
    private val balanceList = mutableListOf<BalanceItem>()
    private val categoryList = mutableListOf<CategoryItem>()
    private val menuList = mutableListOf<MenuItem>()
    private val orderList = mutableListOf<OrderItem>()
    private val saleList = mutableListOf<SaleItem>()
    private val codeList = mutableListOf<ValidateItem>()

    init {
        populateLists()
    }

    override fun getAddressList(key: String): MutableList<AddressItem> {
        println("getAddressList")
        return addressList
    }

    override fun addAddress(key: String, address: AddressItem): AddressItem {
        println("added $address")
        addressList.add(address)
        return addressList.last()
    }

    override fun updateAddress(key: String, address: AddressItem): AddressItem {
        println("updated $address")
        val index = addressList.indexOfFirst { it.id == address.id }
        addressList[index] = address
        return addressList[index]
    }

    override fun deleteAddress(key: String, id: Int) {
        println("deleted address $id")
        addressList.removeIf { it.id == id }
    }

    override fun getBalanceList(key: String, isLast: Boolean): MutableList<BalanceItem> {
        println("getBalanceList isLast - $isLast")
        return if (isLast) {
            println("last item is ${balanceList.maxBy { it.id ?: 0 }}")
            listOf(balanceList.maxBy { it.id ?: 0 }) as MutableList
        } else {
            balanceList
        }
    }

    override fun addBalance(key: String, balanceItem: BalanceItem): BalanceItem {
        println("balanceList size is ${balanceList.size}")
        println("added $balanceItem")
        balanceItem.amount = balanceList.last().amount!! + balanceItem.amountAdded
        balanceList.add(balanceItem)
        println("balanceList size is ${balanceList.size}")
        println("last item is ${balanceList.last()}")
        return balanceList.last()
    }

    override fun validateCode(key: String, code: String): ValidateItem {
        println("validateCode $code")
        return codeList.firstOrNull { it.code == code } ?: ValidateItem(code = code, validated = false, amountAdded = 0)
    }

    override fun getCategoryList(key: String): MutableList<CategoryItem> {
        println("getCategoryList")
        return categoryList
    }

    override fun getMenuList(key: String, idList: String?): MutableList<MenuItem> {
        println("getMenuList ${idList ?: "all"}")

        return if (idList == null) {
            menuList
        } else {
            menuList.filter { menuItem ->
                val list = idList.split(",").map { it.toInt() }
                menuItem.id in list
            } as MutableList<MenuItem>
        }
    }

    override fun getOrderList(key: String): MutableList<OrderItem> {
        println("getOrderList")
        return orderList.onEach { order ->
            order.isActive = Date().time < OtusKitchenApp.getDateFromIsoString(order.date).time + 1000*60*10
        }
    }


    override fun addOrder(key: String, orderItem: OrderItem): OrderItem {
        println("orderList size is ${orderList.size}")
        println("added $orderItem")
        orderList.add(orderItem)
        println("orderList size is ${orderList.size}")
        println("last item in orderList is ${orderList.last()}")
        return orderList.last()
    }

    override fun getSaleList(key: String): MutableList<SaleItem> {
        println("getSaleList")
        return saleList
    }


    override fun getApiKey(key: String): ValidateKey {
        return if (validKey.key == key) {
            ValidateKey(
                key = key,
                isValid = true
            )
        } else {
            validKey.key = generateKey()
            ValidateKey(
                key = validKey.key,
                isValid = true
            )
        }
    }

    private fun generateKey(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..STRING_LENGTH)
            .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
            .joinToString("")
    }

    private fun populateLists() {
        println("populateLists")

        categoryList.addAll(
            mutableListOf(
                CategoryItem(id = 0, name = "Пицца"),
                CategoryItem(id = 1, name = "Закуски"),
                CategoryItem(id = 2, name = "Десерты"),
                CategoryItem(id = 3, name = "Соусы"),
                CategoryItem(id = 4, name = "Напитки"),
            )
        )

        balanceList.addAll(
            mutableListOf(
                BalanceItem(id = 0, orderId = -1, date = "14.08.2022 15:44", amountAdded = 1000, amount = 1000),
                BalanceItem(id = 1, orderId = 0, date = "14.08.2022 16:16", amountAdded = 262, amount = 1262),
                BalanceItem(id = 2, orderId = 1, date = "15.10.2022 17:05", amountAdded = 74, amount = 1336),
                BalanceItem(id = 3, orderId = 2, date = "23.12.2022 21:33", amountAdded = 98, amount = 1434),
                BalanceItem(id = 4, orderId = 3, date = "05.01.2023 15:24", amountAdded = 125, amount = 1559),
                BalanceItem(id = 5, orderId = 4, date = "06.01.2023 18:32", amountAdded = 38, amount = 1597),
                BalanceItem(id = 6, orderId = 5, date = "08.02.2023 17:55", amountAdded = -360, amount = 1237),
            )
        )

        orderList.addAll(
            mutableListOf(
                OrderItem(
                    id = 0,
                    price = 2620,
                    tokensAmount = 262,
                    address = "Ивановский проспект, 4-44",
                    date = "14.08.2022 16:16",
                    isActive = false,
                    positions = listOf(
                        OrderPosition(groupId = 0, sizeId = 0, amount = 1),
                        OrderPosition(groupId = 1, sizeId = 4, amount = 2),
                        OrderPosition(groupId = 2, sizeId = 7, amount = 3),
                    )
                ),
                OrderItem(
                    id = 1,
                    price = 740,
                    tokensAmount = 74,
                    address = "Ивановский проспект, 4-44",
                    date = "15.10.2022 17:05",
                    isActive = false,
                    positions = listOf(OrderPosition(groupId = 0, sizeId = 0, amount = 1))
                ),
                OrderItem(
                    id = 2,
                    price = 980,
                    tokensAmount = 98,
                    address = "Ивановский проспект, 4-44",
                    date = "23.12.2022 21:33",
                    isActive = false,
                    positions = listOf(OrderPosition(groupId = 0, sizeId = 0, amount = 1))
                ),
                OrderItem(
                    id = 3,
                    price = 1250,
                    tokensAmount = 125,
                    address = "Ивановский проспект, 4-44",
                    date = "05.01.2023 15:24",
                    isActive = false,
                    positions = listOf(OrderPosition(groupId = 0, sizeId = 0, amount = 1))
                ),
                OrderItem(
                    id = 4,
                    price = 380,
                    tokensAmount = 38,
                    address = "Ивановский проспект, 4-44",
                    date = "06.01.2023 18:32",
                    isActive = false,
                    positions = listOf(OrderPosition(groupId = 0, sizeId = 0, amount = 1))
                ),
                OrderItem(
                    id = 5,
                    price = 360,
                    tokensAmount = -360,
                    address = "Ивановский проспект, 4-44",
                    date = "08.02.2023 17:55",
                    isActive = false,
                    positions = listOf(OrderPosition(groupId = 0, sizeId = 0, amount = 1))
                ),
                OrderItem(
                    id = 6,
                    price = 670,
                    tokensAmount = 67,
                    address = "Ивановский проспект, 4-44",
                    date = "28.03.2023 14:52",
                    isActive = true,
                    positions = listOf(OrderPosition(groupId = 0, sizeId = 0, amount = 1))
                ),
            )
        )

        menuList.addAll(
            mutableListOf(
                MenuItem(
                    id = 0, // для каждой позиции в меню свой
                    categoryId = 0, // совпадает с категорией выше
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/full/Pepperoni-traditional.webp", // ссылка на картинку
                    name = "Пепперони", // Название позиции
                    description = "Американская классика с пикантной пепперони, Моцареллой и фирменным томатным соусом", // Описание позиции
                    foodValue = MenuItemFoodValue("243", "11", "12", "22"), // КБЖУ на 100г
                    subItems = listOf( // Три вида размера
                        MenuSubItem(
                            0, // сквозная нумерация для ВСЕХ MenuSubItem в списке
                            470, // Цена
                            "23 см", // Отображаемое имя
                            360
                        ),
                        MenuSubItem(
                            1,
                            670,
                            "30 см",
                            600
                        ),
                        MenuSubItem(
                            2,
                            840,
                            "35 см",
                            700
                        )
                    ),
                ),
                MenuItem(
                    id = 1,
                    categoryId = 0,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/full/Meat-traditional.webp",
                    name = "Мясная",
                    description = "Супермясная пицца с пикантной пепперони, ветчиной, хрустящим беконом, ароматной свининой, говядиной, Моцареллой и фирменным томатным соусом",
                    foodValue = MenuItemFoodValue("256", "6", "10", "34"), // БЖУ для 100г
                    subItems = listOf(
                        MenuSubItem(
                            3,
                            570,
                            "23 см",
                            400
                        ),
                        MenuSubItem(
                            4,
                            810,
                            "30 см",
                            650
                        ),
                        MenuSubItem(
                            5,
                            980,
                            "35 см",
                            900
                        )
                    ),
                ),
                MenuItem(
                    id = 2,
                    categoryId = 0,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/full/Chicken-BBQ-traditional.webp",
                    name = "Цыпленок Барбекю",
                    description = "Сочное куриное филе и хрустящий бекон в сочетании с фирменным томатным соусом, Моцареллой и луком",
                    foodValue = MenuItemFoodValue("263", "9", "15", "23"),
                    subItems = listOf(
                        MenuSubItem(
                            6,
                            500,
                            "23 см",
                            400
                        ),
                        MenuSubItem(
                            7,
                            770,
                            "30 см",
                            650
                        ),
                        MenuSubItem(
                            8,
                            900,
                            "35 см",
                            900
                        )
                    )
                ),
                MenuItem(
                    id = 3,
                    categoryId = 0,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/full/8036e41d989a3b6e9d04506bb7e8476a.webp",
                    name = "Мексиканская",
                    description = "Острая пицца с куриным филе, фирменным томатным соусом, Моцареллой, шампиньонами, луком, томатами, сладким перцем и перцем \"Халапеньо\"",
                    foodValue = MenuItemFoodValue("175", "11", "5", "21"),
                    subItems = listOf(
                        MenuSubItem(
                            9,
                            500,
                            "23 см",
                            400
                        ),
                        MenuSubItem(
                            10,
                            770,
                            "30 см",
                            650
                        ),
                        MenuSubItem(
                            11,
                            900,
                            "35 см",
                            900
                        )
                    )
                ),
                MenuItem(
                    id = 4,
                    categoryId = 0,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/full/Hawai-traditional.webp",
                    name = "Гавайская",
                    description = "Тропическая классика с ароматной ветчиной, фирменным томатным соусом и Моцареллой в сочетании с кусочками ананаса",
                    foodValue = MenuItemFoodValue("183", "10", "6", "22"),
                    subItems = listOf(
                        MenuSubItem(
                            12,
                            500,
                            "23 см",
                            400
                        ),
                        MenuSubItem(
                            13,
                            770,
                            "30 см",
                            650
                        ),
                        MenuSubItem(
                            14,
                            900,
                            "35 см",
                            900
                        )
                    )
                ),
                MenuItem(
                    id = 5,
                    categoryId = 0,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/full/Margherita-traditional.webp",
                    name = "Маргарита",
                    description = "Традиционный рецепт пиццы с Моцареллой, сочными томатами, фирменным томатным соусом и орегано",
                    foodValue = MenuItemFoodValue("185", "10", "7", "20"),
                    subItems = listOf(
                        MenuSubItem(
                            15,
                            430,
                            "23 см",
                            380
                        ),
                        MenuSubItem(
                            16,
                            660,
                            "30 см",
                            600
                        ),
                        MenuSubItem(
                            17,
                            800,
                            "35 см",
                            800
                        )
                    )
                ),
                MenuItem(
                    id = 6,
                    categoryId = 0,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/full/Ham-and-mushroom-traditional.webp",
                    name = "Ветчина и грибы",
                    description = "Нежная пицца с соусом \"Чесночный Рэнч\", Моцареллой, шампиньонами, ароматной ветчиной и чесноком",
                    foodValue = MenuItemFoodValue("235", "11", "10", "26"),
                    subItems = listOf(
                        MenuSubItem(
                            18,
                            470,
                            "23 см",
                            340
                        ),
                        MenuSubItem(
                            19,
                            670,
                            "30 см",
                            520
                        ),
                        MenuSubItem(
                            20,
                            840,
                            "35 см",
                            750
                        )
                    )
                ),
                MenuItem(
                    id = 7,
                    categoryId = 1,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/2ba4b89b2e4cd556726df174473b7322.webp",
                    name = "Куриные крылышки Барбекю",
                    description = "Куриные крылышки в соусе \"Барбекю\"",
                    foodValue = MenuItemFoodValue("530", "24", "39", "20"),
                    subItems = listOf(
                        MenuSubItem(
                            21,
                            260,
                            "S",
                            130
                        ),
                        MenuSubItem(
                            22,
                            480,
                            "M",
                            260
                        ),
                        MenuSubItem(
                            23,
                            730,
                            "L",
                            390
                        )
                    )
                ),
                MenuItem(
                    id = 8,
                    categoryId = 1,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/Potatoe%20wadges.jpg",
                    name = "Картофельные дольки",
                    description = "Запеченные дольки картофеля в мундире со специями и соусом на выбор",
                    foodValue = MenuItemFoodValue("70", "1", "2", "12"),
                    subItems = listOf(
                        MenuSubItem(
                            105,
                            190,
                            "S",
                            190
                        ),
                        MenuSubItem(
                            106,
                            290,
                            "M",
                            290
                        ),
                        MenuSubItem(
                            107,
                            380,
                            "L",
                            380
                        )
                    )
                ),
                MenuItem(
                    id = 9,
                    categoryId = 1,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/Chicken%20poppers%20small.jpg",
                    name = "Чикен попкорн",
                    description = "Куриное филе с соусом на выбор",
                    foodValue = MenuItemFoodValue("70", "1", "2", "12"),
                    subItems = listOf(
                        MenuSubItem(
                            24,
                            230,
                            "10 шт",
                            190
                        ),
                        MenuSubItem(
                            25,
                            460,
                            "20 шт",
                            380
                        ),
                        MenuSubItem(
                            26,
                            690,
                            "30 шт",
                            570
                        )
                    )
                ),
                MenuItem(
                    id = 10,
                    categoryId = 1,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/full/d48f86a4109655d21ccee8803aee07bb.webp",
                    name = "Тортилья чикен",
                    description = "Ролл с Моцареллой, сочным куриным филе, томатами, шампиньонами и соусом \"Чесночный Рэнч\"",
                    foodValue = MenuItemFoodValue("170", "8", "9", "19"),
                    subItems = listOf(
                        MenuSubItem(
                            27,
                            140,
                            "S",
                            190
                        ),
                        MenuSubItem(
                            28,
                            220,
                            "M",
                            380
                        ),
                        MenuSubItem(
                            29,
                            360,
                            "L",
                            570
                        )
                    )
                ),
                MenuItem(
                    id = 11,
                    categoryId = 1,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/15cb1517676abd49315e7506d52289f3.webp",
                    name = "Рогалики с колбасками",
                    description = "Рогалики из нежного теста, с чесночным соусом, альпийскими колбасками и сыром моцарелла",
                    foodValue = MenuItemFoodValue("360", "11", "22", "30"),
                    subItems = listOf(
                        MenuSubItem(
                            30,
                            230,
                            "5 шт",
                            180
                        ),
                        MenuSubItem(
                            31,
                            460,
                            "10 шт",
                            360
                        ),
                        MenuSubItem(
                            32,
                            690,
                            "15 шт",
                            540
                        )
                    )
                ),
                MenuItem(
                    id = 12,
                    categoryId = 2,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/fed3dbf7f5a82e6d3e59b005fd9bfd67.webp",
                    name = "Донат Ягодный",
                    description = "Пончик с ягодной начинкой. Ярко-розовые пончики с начинкой из ягод. Вкус спелых ягод наполняет радостью и летним настроением.",
                    foodValue = MenuItemFoodValue("80", "1", "2", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            33,
                            100,
                            "1 шт",
                            100
                        ),
                        MenuSubItem(
                            34,
                            200,
                            "2 шт",
                            200
                        ),
                        MenuSubItem(
                            35,
                            300,
                            "3 шт",
                            300
                        )
                    )
                ),
                MenuItem(
                    id = 13,
                    categoryId = 2,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/6f39f08c6b132ec84999ef2e3693cfa1.webp",
                    name = "Донат Шоколадный",
                    description = "Свежий ароматный донат, покрытый шоколадной глазурью. Классический вкус шоколада подарит вам ощущение уюта и комфорта.",
                    foodValue = MenuItemFoodValue("80", "1", "2", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            102,
                            100,
                            "1 шт",
                            100
                        ),
                        MenuSubItem(
                            103,
                            200,
                            "2 шт",
                            200
                        ),
                        MenuSubItem(
                            104,
                            300,
                            "3 шт",
                            300
                        )
                    )
                ),
                MenuItem(
                    id = 14,
                    categoryId = 2,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/1b83a36c8cdeb2d399f1837780308ba8.webp",
                    name = "Орео роллы",
                    description = "Роллы с печеньем Орео, сливочным сыром и нежной сахарной глазурью.",
                    foodValue = MenuItemFoodValue("80", "1", "2", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            36,
                            140,
                            "4 шт",
                            140
                        ),
                        MenuSubItem(
                            37,
                            270,
                            "8 шт",
                            270
                        ),
                        MenuSubItem(
                            38,
                            390,
                            "12 шт",
                            410
                        )
                    )
                ),
                MenuItem(
                    id = 15,
                    categoryId = 2,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/b888372cda043e9c5cf181e2c00dfd87.webp",
                    name = "Шоко роллы",
                    description = "Много шоколада! Роллы с шоколадной начинкой, политые шоколадом с шоколадной крошкой",
                    foodValue = MenuItemFoodValue("80", "1", "2", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            39,
                            140,
                            "4 шт",
                            140
                        ),
                        MenuSubItem(
                            40,
                            270,
                            "8 шт",
                            270
                        ),
                        MenuSubItem(
                            41,
                            390,
                            "12 шт",
                            410
                        )
                    )
                ),
                MenuItem(
                    id = 16,
                    categoryId = 2,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/4ad828e49c9a48dbb71a0066ebe19162.webp",
                    name = "Роллы с яблоком и корицей",
                    description = "Фирменное тесто, карамель, зеленое яблоко, корица",
                    foodValue = MenuItemFoodValue("80", "1", "2", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            42,
                            120,
                            "4 шт",
                            140
                        ),
                        MenuSubItem(
                            43,
                            240,
                            "8 шт",
                            270
                        ),
                        MenuSubItem(
                            44,
                            350,
                            "12 шт",
                            410
                        )
                    )
                ),
                MenuItem(
                    id = 17,
                    categoryId = 2,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/c0cdd75c434af3236fbb29864f38514c.webp",
                    name = "Чизкейк Шоколадный",
                    description = "Натуральный горький шоколад, смешанный с нежным крем-чизом, дают потрясающий вкус, немного напоминающий сливочное шоколадное мороженое. Мягкая сырная масса удачно сочетается с хрустящим песочным коржом и не оставит Вас равнодушным.",
                    foodValue = MenuItemFoodValue("382", "4", "24", "26"),
                    subItems = listOf(
                        MenuSubItem(
                            45,
                            190,
                            "100 гр",
                            100
                        ),
                        MenuSubItem(
                            46,
                            380,
                            "200 гр",
                            200
                        ),
                        MenuSubItem(
                            47,
                            570,
                            "300 гр",
                            300
                        )
                    )
                ),
                MenuItem(
                    id = 18,
                    categoryId = 2,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/584%D1%85460-Starters-NewYork1-1.webp",
                    name = "Чизкейк \"Нью-Йорк\"",
                    description = "Классический чизкейк из сливочного сыра, с добавлением натуральной ванили и свежей мяты.",
                    foodValue = MenuItemFoodValue("360", "4", "24", "20"),
                    subItems = listOf(
                        MenuSubItem(
                            48,
                            190,
                            "1 шт",
                            125
                        ),
                        MenuSubItem(
                            49,
                            380,
                            "2 шт",
                            250
                        ),
                        MenuSubItem(
                            50,
                            570,
                            "3 шт",
                            375
                        )
                    )
                ),
                MenuItem(
                    id = 19,
                    categoryId = 2,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/685b93b4c04ed49469ea8f3c1ddbd9c7.webp",
                    name = "Сырники",
                    description = "Нежные сырники из печи с соусом на выбор",
                    foodValue = MenuItemFoodValue("120", "6", "3", "16"),
                    subItems = listOf(
                        MenuSubItem(
                            51,
                            170,
                            "2 шт",
                            160
                        ),
                        MenuSubItem(
                            52,
                            340,
                            "4 шт",
                            320
                        ),
                        MenuSubItem(
                            53,
                            510,
                            "6 шт",
                            480
                        )
                    )
                ),
                MenuItem(
                    id = 20,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/00090d1d169059c6913de46440f5d05a.webp",
                    name = "Томатный",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            54,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            55,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            56,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 21,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/584%D1%85460-Sauce-runch.webp",
                    name = "Чесночный",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            57,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            58,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            59,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 22,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/584%D1%85460-Sauce-island.webp",
                    name = "Сладкий чили",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            60,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            61,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            62,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 23,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/1019e42db58f0e8e362963744bb16c78.webp",
                    name = "Барбекю",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            63,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            64,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            65,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 24,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/584%D1%85460-Sauce-runch.webp",
                    name = "1000 островов",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            66,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            67,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            68,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 25,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/4258a50e42aaf80a02a9cf56e60ab664.webp",
                    name = "Сгущенка",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            69,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            70,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            71,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 26,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/301a0f795919a7b618038b638dde68bc.webp",
                    name = "Джем",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            72,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            73,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            74,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 27,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/1019e42db58f0e8e362963744bb16c78.webp",
                    name = "Шоколадный",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            75,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            76,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            77,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 28,
                    categoryId = 3,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/584%D1%85460-Sauce-runch.webp",
                    name = "Сметана",
                    description = "",
                    foodValue = MenuItemFoodValue("40", "1", "0", "9"),
                    subItems = listOf(
                        MenuSubItem(
                            78,
                            30,
                            "25 гр",
                            25
                        ),
                        MenuSubItem(
                            79,
                            50,
                            "50 гр",
                            50
                        ),
                        MenuSubItem(
                            80,
                            75,
                            "75 гр",
                            75
                        )
                    )
                ),
                MenuItem(
                    id = 29,
                    categoryId = 4,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/eaa8fdfd7eacc22ccec47a474a83a026.webp",
                    name = "Клубничный молочный коктейль",
                    description = "Десертный напиток на основе молока и мороженого с добавлением клубничного сиропа.",
                    foodValue = MenuItemFoodValue("100", "6", "10", "27"),
                    subItems = listOf(
                        MenuSubItem(
                            81,
                            100,
                            "200 мл",
                            200
                        ),
                        MenuSubItem(
                            82,
                            160,
                            "300 мл",
                            300
                        ),
                        MenuSubItem(
                            83,
                            240,
                            "400 мл",
                            400
                        )
                    )
                ),
                MenuItem(
                    id = 30,
                    categoryId = 4,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/767d0ffbec2de32caaf33d6fe52830dc.webp",
                    name = "Шоколадный молочный коктейль",
                    description = "Десертный напиток на основе молока и мороженого с добавлением шоколадного сиропа.",
                    foodValue = MenuItemFoodValue("100", "6", "10", "27"),
                    subItems = listOf(
                        MenuSubItem(
                            84,
                            100,
                            "200 мл",
                            200
                        ),
                        MenuSubItem(
                            85,
                            160,
                            "300 мл",
                            300
                        ),
                        MenuSubItem(
                            86,
                            240,
                            "400 мл",
                            400
                        )
                    )
                ),
                MenuItem(
                    id = 31,
                    categoryId = 4,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/30435758c80e13d33195eb4ba141851e.webp",
                    name = "Ванильный молочный коктейль",
                    description = "Десертный напиток на основе молока и мороженого с добавлением шоколадного сиропа.",
                    foodValue = MenuItemFoodValue("100", "6", "10", "27"),
                    subItems = listOf(
                        MenuSubItem(
                            87,
                            100,
                            "200 мл",
                            200
                        ),
                        MenuSubItem(
                            88,
                            160,
                            "300 мл",
                            300
                        ),
                        MenuSubItem(
                            89,
                            240,
                            "400 мл",
                            400
                        )
                    )
                ),
                MenuItem(
                    id = 32,
                    categoryId = 4,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/156f73048e262153aa12d3820caaa73d.webp",
                    name = "Сок \"Я\" Апельсиновый",
                    description = "Сок Апельсиновый из Аранжевых Апельсинов ;D ",
                    foodValue = MenuItemFoodValue("45", "0", "0", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            90,
                            50,
                            "200 мл",
                            200
                        ),
                        MenuSubItem(
                            91,
                            100,
                            "500 мл",
                            500
                        ),
                        MenuSubItem(
                            92,
                            200,
                            "1000 мл",
                            1000
                        )
                    )
                ),
                MenuItem(
                    id = 33,
                    categoryId = 4,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/a988057a4dfb9d046cebe309255f204f.webp",
                    name = "Сок \"Я\" Вишневый",
                    description = "Сок Вишневый",
                    foodValue = MenuItemFoodValue("45", "0", "0", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            93,
                            50,
                            "200 мл",
                            200
                        ),
                        MenuSubItem(
                            94,
                            100,
                            "500 мл",
                            500
                        ),
                        MenuSubItem(
                            95,
                            200,
                            "1000 мл",
                            1000
                        )
                    )
                ),
                MenuItem(
                    id = 34,
                    categoryId = 4,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/e3124dd8cdbf945fab9c46ad4068f90b.webp",
                    name = "Сок \"Я\" Грейпфрутовый",
                    description = "Сок Грейпфрутовый",
                    foodValue = MenuItemFoodValue("45", "0", "0", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            96,
                            50,
                            "200 мл",
                            200
                        ),
                        MenuSubItem(
                            97,
                            100,
                            "500 мл",
                            500
                        ),
                        MenuSubItem(
                            98,
                            200,
                            "1000 мл",
                            1000
                        )
                    )
                ),
                MenuItem(
                    id = 35,
                    categoryId = 4,
                    picture = "https://cdn.papajohns.ru//images/catalog/thumbs/cart/d4cc2da3f54aa309c0e5ff3ed710fdcd.webp",
                    name = "Сок \"Я\" Яблочный",
                    description = "Сок Яблочный",
                    foodValue = MenuItemFoodValue("45", "0", "0", "11"),
                    subItems = listOf(
                        MenuSubItem(
                            99,
                            50,
                            "200 мл",
                            200
                        ),
                        MenuSubItem(
                            100,
                            100,
                            "500 мл",
                            500
                        ),
                        MenuSubItem(
                            101,
                            200,
                            "1000 мл",
                            1000
                        )
                    )
                )
            )
        )

        addressList.addAll(
            mutableListOf(
                AddressItem(id = 0, name = "Дом", address = "Ивановский проспект, 4-44"),
                AddressItem(id = 1, name = "Работа", address = "Рабочая, 12 оф.542"),
            )
        )

        codeList.addAll(
            mutableListOf(
                ValidateItem(code = "WELCOME", validated = false, amountAdded = 1000),
                ValidateItem(code = "88005553535", validated = true, amountAdded = 300)
            )
        )
    }

    companion object {
        const val STRING_LENGTH = 10
    }
}