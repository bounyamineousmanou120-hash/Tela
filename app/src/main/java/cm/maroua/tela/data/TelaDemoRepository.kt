package cm.maroua.tela.data

object TelaDemoRepository {
    val clients = listOf(
        TelaClient(1, "AK", "Aïcha Kolodji", "+237 677 123 456", "Domayo", 4, 0xFFF4BE62),
        TelaClient(2, "FM", "Fanta Moussa", "+237 699 234 567", "Pitoaré", 7, 0xFF9B59C7),
        TelaClient(3, "MB", "Mariama Bello", "+237 655 345 678", "Djarengol", 2, 0xFFEF4444),
        TelaClient(4, "HY", "Halima Yaya", "+237 670 456 789", "Marché central", 5, 0xFF2ECC71),
    )

    private val defaultMeasurements = listOf(
        TelaMeasurement("Poitrine", "92 cm"),
        TelaMeasurement("Taille", "74 cm"),
        TelaMeasurement("Hanches", "96 cm"),
        TelaMeasurement("Épaules", "38 cm"),
        TelaMeasurement("Longueur", "110 cm"),
        TelaMeasurement("Manche", "58 cm"),
    )

    val orders = listOf(
        TelaOrder(
            id = 42,
            commandNumber = "#0042",
            clientInitials = "AK",
            clientName = "Aïcha Kolodji",
            clothingType = "Boubou brodé",
            orderedAt = "20 mai 2025",
            deliveryAt = "5 juin 2025",
            status = TelaOrderStatus.InProgress,
            measurements = defaultMeasurements,
            progress = listOf(
                TelaProgressStep("Prise de mesures", true),
                TelaProgressStep("Coupe du tissu", true),
                TelaProgressStep("Assemblage", true),
                TelaProgressStep("Finition", false),
                TelaProgressStep("Livraison", false),
            ),
        ),
        TelaOrder(
            id = 43,
            commandNumber = "#0043",
            clientInitials = "FM",
            clientName = "Fanta Moussa",
            clothingType = "Robe de mariage",
            orderedAt = "25 mai 2025",
            deliveryAt = "10 juin 2025",
            status = TelaOrderStatus.Completed,
            measurements = defaultMeasurements,
            progress = listOf(
                TelaProgressStep("Prise de mesures", true),
                TelaProgressStep("Coupe du tissu", true),
                TelaProgressStep("Assemblage", true),
                TelaProgressStep("Finition", true),
                TelaProgressStep("Livraison", false),
            ),
        ),
        TelaOrder(
            id = 44,
            commandNumber = "#0044",
            clientInitials = "MB",
            clientName = "Mariama Bello",
            clothingType = "Tenue 3 pièces",
            orderedAt = "22 mai 2025",
            deliveryAt = "18 juin 2025",
            status = TelaOrderStatus.Pending,
            measurements = defaultMeasurements,
            progress = listOf(
                TelaProgressStep("Prise de mesures", true),
                TelaProgressStep("Coupe du tissu", false),
                TelaProgressStep("Assemblage", false),
                TelaProgressStep("Finition", false),
                TelaProgressStep("Livraison", false),
            ),
        ),
    )

    val profile = TelaWorkshopProfile(
        tailorInitials = "MA",
        tailorName = "Moussa Alhadji",
        email = "moussa.alhadji@tela-maroua.cm",
        workshopName = "Atelier Têla",
        location = "Marché central, Maroua",
        specialties = "Boubous, robes, costumes",
        workedHours = "120h",
    )

    val dashboardStats = TelaDashboardStats(
        monthlyOrders = 14,
        totalClients = clients.size + 43,
        deliveredThisMonth = 8,
    )

    val featuredOrder: TelaOrder = orders.first()
}
