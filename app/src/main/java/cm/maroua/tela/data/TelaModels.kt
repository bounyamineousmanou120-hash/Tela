package cm.maroua.tela.data

data class TelaClient(
    val id: Long,
    val initials: String,
    val fullName: String,
    val phone: String,
    val district: String,
    val orderCount: Int,
    val avatarColor: Long,
)

data class TelaMeasurement(
    val label: String,
    val value: String,
)

data class TelaProgressStep(
    val label: String,
    val isCompleted: Boolean,
)

enum class TelaOrderStatus(val label: String) {
    Pending("En attente"),
    InProgress("En cours"),
    Completed("Terminé"),
    Delivered("Livré"),
}

data class TelaOrder(
    val id: Long,
    val commandNumber: String,
    val clientInitials: String,
    val clientName: String,
    val clothingType: String,
    val orderedAt: String,
    val deliveryAt: String,
    val status: TelaOrderStatus,
    val measurements: List<TelaMeasurement>,
    val progress: List<TelaProgressStep>,
)

data class TelaWorkshopProfile(
    val tailorInitials: String,
    val tailorName: String,
    val email: String,
    val workshopName: String,
    val location: String,
    val specialties: String,
    val workedHours: String,
)

data class TelaDashboardStats(
    val monthlyOrders: Int,
    val totalClients: Int,
    val deliveredThisMonth: Int,
)
