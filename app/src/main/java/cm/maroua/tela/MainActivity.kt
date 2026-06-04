package cm.maroua.tela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.maroua.tela.data.TelaClient
import cm.maroua.tela.data.TelaDashboardStats
import cm.maroua.tela.data.TelaDemoRepository
import cm.maroua.tela.data.TelaOrder
import cm.maroua.tela.data.TelaOrderStatus
import cm.maroua.tela.data.TelaWorkshopProfile

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaApp()
        }
    }
}

private val DarkBrown = Color(0xFF6B3517)
private val Brown = Color(0xFF955322)
private val Orange = Color(0xFFC87536)
private val Cream = Color(0xFFF7F1EA)
private val CardWhite = Color(0xFFFFFCF8)
private val Muted = Color(0xFF8D8D8D)
private val Green = Color(0xFF22C55E)
private val Purple = Color(0xFF7C3AED)
private val Red = Color(0xFFEF4444)

@Composable
fun TelaApp() {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = Orange,
            secondary = Brown,
            background = Cream,
            surface = CardWhite,
        ),
    ) {
        var selectedTab by remember { mutableIntStateOf(0) }
        val tabs = listOf("Accueil", "Clients", "Commandes", "Profil")
        val clients = remember { TelaDemoRepository.clients }
        val orders = remember { TelaDemoRepository.orders }
        val stats = remember { TelaDemoRepository.dashboardStats }
        val profile = remember { TelaDemoRepository.profile }
        val featuredOrder = remember { TelaDemoRepository.featuredOrder }

        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = CardWhite) {
                    tabs.forEachIndexed { index, label ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = { Text(tabEmoji(label), fontSize = 20.sp) },
                            label = { Text(label, fontSize = 11.sp) },
                        )
                    }
                }
            },
        ) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                color = Cream,
            ) {
                when (selectedTab) {
                    0 -> HomeScreen(stats = stats, orders = orders)
                    1 -> ClientsScreen(clients = clients)
                    2 -> OrdersScreen(order = featuredOrder)
                    else -> ProfileScreen(profile = profile, stats = stats)
                }
            }
        }
    }
}

private fun tabEmoji(label: String): String = when (label) {
    "Accueil" -> "⌂"
    "Clients" -> "♟"
    "Commandes" -> "▰"
    else -> "●"
}

@Composable
private fun HomeScreen(stats: TelaDashboardStats, orders: List<TelaOrder>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item { HeroHeader() }
        item {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                StatCard(stats.monthlyOrders.toString(), "Commandes", "ce mois", Orange, Modifier.weight(1f))
                StatCard(stats.totalClients.toString(), "Clients", "total", Purple, Modifier.weight(1f))
                StatCard(stats.deliveredThisMonth.toString(), "Livrées", "ce mois", Green, Modifier.weight(1f))
            }
        }
        item { SectionTitle("Commandes récentes", "Voir tout") }
        items(orders) { order -> OrderCard(order) }
        item { SectionTitle("Catégories", null) }
        item {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                CategoryCard("🧵", "Boubou", Modifier.weight(1f))
                CategoryCard("💐", "Mariage", Modifier.weight(1f))
                CategoryCard("💎", "Soirée", Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun HeroHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(listOf(DarkBrown, Orange)),
            )
            .padding(22.dp),
    ) {
        Text("BIENVENUE", color = Color.White.copy(alpha = 0.72f), fontSize = 12.sp)
        Text("Bonjour, Moussa 👋", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Atelier Têla · Maroua", color = Color.White.copy(alpha = 0.82f), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        SearchPill("Rechercher client, commande…")
    }
}

@Composable
private fun SearchPill(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.18f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("⌕", color = Color.White.copy(alpha = 0.8f), fontSize = 18.sp)
        Spacer(Modifier.width(10.dp))
        Text(text, color = Color.White.copy(alpha = 0.78f), fontSize = 14.sp)
    }
}

@Composable
private fun StatCard(value: String, label: String, subLabel: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(value, color = color, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(label, color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(subLabel, color = Muted, fontSize = 10.sp)
        }
    }
}

@Composable
private fun SectionTitle(title: String, action: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        if (action != null) {
            Text(action, color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ClientsScreen(clients: List<TelaClient>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { ScreenTopBar("‹", "Mes Clients", "+") }
        item { LightSearchPill("Rechercher un client…") }
        items(clients) { client -> ClientCard(client) }
    }
}

@Composable
private fun ScreenTopBar(left: String, title: String, right: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CircleButton(left)
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        CircleButton(right)
    }
}

@Composable
private fun CircleButton(text: String) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF3E9DD)),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, color = Orange, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun LightSearchPill(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF2ECE5))
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("⌕", color = Color(0xFFC2936E), fontSize = 18.sp)
        Spacer(Modifier.width(10.dp))
        Text(text, color = Color(0xFFC2936E), fontSize = 14.sp)
    }
}

@Composable
private fun ClientCard(client: TelaClient) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Avatar(client.initials, Color(client.avatarColor))
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(client.fullName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(client.phone, color = Muted, fontSize = 12.sp)
                Text("${client.orderCount} commandes", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CircleButton("☎")
                CircleButton("▣")
            }
        }
    }
}

@Composable
private fun Avatar(initials: String, color: Color) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(color),
        contentAlignment = Alignment.Center,
    ) {
        Text(initials, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
private fun OrdersScreen(order: TelaOrder) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item { OrderDetailHeader(order) }
        item {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                MeasurementsPanel(order)
                ProgressPanel(order)
                PrimaryButton("Mettre à jour l'avancement")
            }
        }
    }
}

@Composable
private fun OrderDetailHeader(order: TelaOrder) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(DarkBrown, Orange)))
            .padding(20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CircleButton("‹")
            Spacer(Modifier.width(12.dp))
            Text("Détail commande", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Text("▮", color = Color.White)
        }
        Spacer(Modifier.height(18.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Avatar(order.clientInitials, avatarColorForInitials(order.clientInitials))
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(order.clientName, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("${order.clothingType} · Cmd ${order.commandNumber}", color = Color.White.copy(alpha = 0.82f), fontSize = 12.sp)
            }
            StatusChip(order.status.label, order.status.statusColor())
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            DateCard("COMMANDÉ LE", order.orderedAt, Modifier.weight(1f))
            DateCard("LIVRAISON", order.deliveryAt, Modifier.weight(1f))
        }
    }
}

@Composable
private fun DateCard(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.16f))
            .padding(12.dp),
    ) {
        Text(label, color = Color.White.copy(alpha = 0.62f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
        Text(value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MeasurementsPanel(order: TelaOrder) {
    InfoPanel(title = "📐  Mesures") {
        order.measurements.chunked(3).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { measurement ->
                    MeasurementItem(measurement.value, measurement.label, Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun MeasurementItem(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Cream)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(value, color = Orange, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Text(label, color = Muted, fontSize = 9.sp)
    }
}

@Composable
private fun ProgressPanel(order: TelaOrder) {
    InfoPanel(title = "✂️  Avancement") {
        order.progress.forEach { step ->
            Row(
                modifier = Modifier.padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(if (step.isCompleted) Orange else Color(0xFFE5E0DA)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(if (step.isCompleted) "✓" else "", color = Color.White, fontSize = 13.sp)
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    step.label,
                    color = if (step.isCompleted) Color.Black else Muted,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
private fun InfoPanel(title: String, content: @Composable Column.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun PrimaryButton(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Orange)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
private fun OrderCard(order: TelaOrder) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Avatar(order.clientInitials, avatarColorForInitials(order.clientInitials))
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(order.clientName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("${order.clothingType} · ${order.orderedAt}", color = Muted, fontSize = 12.sp)
            }
            StatusChip(order.status.label, order.status.statusColor())
        }
    }
}

@Composable
private fun StatusChip(text: String, color: Color) {
    Text(
        text = "● $text",
        color = color,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 5.dp),
    )
}

@Composable
private fun CategoryCard(icon: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(icon, fontSize = 20.sp)
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ProfileScreen(profile: TelaWorkshopProfile, stats: TelaDashboardStats) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CardWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(44.dp))
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(Orange),
            contentAlignment = Alignment.Center,
        ) {
            Text(profile.tailorInitials, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(10.dp))
        Text(profile.tailorName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(profile.email, color = Muted, fontSize = 12.sp)
        Spacer(Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
            ProfileStat(stats.totalClients.toString(), "Clients")
            ProfileStat(stats.monthlyOrders.toString(), "Commandes")
            ProfileStat(profile.workedHours, "Travaillées")
        }
        Spacer(Modifier.height(28.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Cream)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProfileMenu("📍", profile.workshopName, profile.location)
            ProfileMenu("🧶", "Mes spécialités", profile.specialties)
            ProfileMenu("🧾", "Mes tarifs", "Voir la grille tarifaire")
            ProfileMenu("🕘", "Aide & support", "Centre d'assistance Têla")
        }
    }
}

@Composable
private fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = Orange, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, color = Muted, fontSize = 10.sp)
    }
}

@Composable
private fun ProfileMenu(icon: String, title: String, subtitle: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFF1DC)),
                contentAlignment = Alignment.Center,
            ) { Text(icon, fontSize = 18.sp) }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(subtitle, color = Muted, fontSize = 11.sp)
            }
            Text("›", color = Muted, fontSize = 22.sp)
        }
    }
}

private fun TelaOrderStatus.statusColor(): Color = when (this) {
    TelaOrderStatus.Pending -> Purple
    TelaOrderStatus.InProgress -> Color(0xFFF59E0B)
    TelaOrderStatus.Completed -> Green
    TelaOrderStatus.Delivered -> Orange
}

private fun avatarColorForInitials(initials: String): Color = when (initials) {
    "AK" -> Color(0xFFF4BE62)
    "FM" -> Color(0xFF9B59C7)
    "MB" -> Red
    "HY" -> Color(0xFF2ECC71)
    else -> Orange
}

@Preview(showBackground = true)
@Composable
private fun TelaAppPreview() {
    TelaApp()
}
