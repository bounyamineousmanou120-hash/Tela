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

private data class ClientUi(
    val initials: String,
    val name: String,
    val phone: String,
    val orders: Int,
    val color: Color,
)

private data class OrderUi(
    val clientInitials: String,
    val clientName: String,
    val title: String,
    val date: String,
    val status: String,
    val statusColor: Color,
)

private val demoClients = listOf(
    ClientUi("AK", "Aïcha Kolodji", "+237 677 123 456", 4, Color(0xFFF4BE62)),
    ClientUi("FM", "Fanta Moussa", "+237 699 234 567", 7, Color(0xFF9B59C7)),
    ClientUi("MB", "Mariama Bello", "+237 655 345 678", 2, Red),
    ClientUi("HY", "Halima Yaya", "+237 670 456 789", 5, Color(0xFF2ECC71)),
)

private val demoOrders = listOf(
    OrderUi("AK", "Aïcha Kolodji", "Boubou brodé", "28 mai", "En cours", Color(0xFFF59E0B)),
    OrderUi("FM", "Fanta Moussa", "Robe de mariage", "25 mai", "Terminé", Green),
    OrderUi("MB", "Mariama Bello", "Tenue 3 pièces", "22 mai", "En attente", Purple),
)

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
                    0 -> HomeScreen()
                    1 -> ClientsScreen()
                    2 -> OrdersScreen()
                    else -> ProfileScreen()
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
private fun HomeScreen() {
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
                StatCard("14", "Commandes", "ce mois", Orange, Modifier.weight(1f))
                StatCard("47", "Clients", "total", Purple, Modifier.weight(1f))
                StatCard("8", "Livrées", "ce mois", Green, Modifier.weight(1f))
            }
        }
        item { SectionTitle("Commandes récentes", "Voir tout") }
        items(demoOrders) { order -> OrderCard(order) }
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
private fun ClientsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { ScreenTopBar("‹", "Mes Clients", "+") }
        item { LightSearchPill("Rechercher un client…") }
        items(demoClients) { client -> ClientCard(client) }
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
private fun ClientCard(client: ClientUi) {
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
            Avatar(client.initials, client.color)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(client.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(client.phone, color = Muted, fontSize = 12.sp)
                Text("${client.orders} commandes", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
private fun OrdersScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item { OrderDetailHeader() }
        item {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                MeasurementsPanel()
                ProgressPanel()
                PrimaryButton("Mettre à jour l'avancement")
            }
        }
    }
}

@Composable
private fun OrderDetailHeader() {
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
            Avatar("AK", Color(0xFFF4BE62))
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Aïcha Kolodji", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Boubou brodé · Cmd #0042", color = Color.White.copy(alpha = 0.82f), fontSize = 12.sp)
            }
            StatusChip("En cours", Color(0xFFF59E0B))
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            DateCard("COMMANDÉ LE", "20 mai 2025", Modifier.weight(1f))
            DateCard("LIVRAISON", "5 juin 2025", Modifier.weight(1f))
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
private fun MeasurementsPanel() {
    InfoPanel(title = "📐  Mesures") {
        val measures = listOf(
            "92 cm" to "Poitrine",
            "74 cm" to "Taille",
            "96 cm" to "Hanches",
            "38 cm" to "Épaules",
            "110 cm" to "Longueur",
            "58 cm" to "Manche",
        )
        measures.chunked(3).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { item ->
                    MeasurementItem(item.first, item.second, Modifier.weight(1f))
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
private fun ProgressPanel() {
    InfoPanel(title = "✂️  Avancement") {
        listOf("Prise de mesures", "Coupe du tissu", "Assemblage").forEach { step ->
            Row(
                modifier = Modifier.padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(Orange),
                    contentAlignment = Alignment.Center,
                ) { Text("✓", color = Color.White, fontSize = 13.sp) }
                Spacer(Modifier.width(12.dp))
                Text(step, fontWeight = FontWeight.Bold, fontSize = 14.sp)
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
private fun OrderCard(order: OrderUi) {
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
            Avatar(order.clientInitials, if (order.clientInitials == "AK") Color(0xFFF4BE62) else if (order.clientInitials == "FM") Color(0xFF9B59C7) else Red)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(order.clientName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("${order.title} · ${order.date}", color = Muted, fontSize = 12.sp)
            }
            StatusChip(order.status, order.statusColor)
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
private fun ProfileScreen() {
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
            Text("MA", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(10.dp))
        Text("Moussa Alhadji", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("moussa.alhadji@tela-maroua.cm", color = Muted, fontSize = 12.sp)
        Spacer(Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
            ProfileStat("47", "Clients")
            ProfileStat("14", "Commandes")
            ProfileStat("120h", "Travaillées")
        }
        Spacer(Modifier.height(28.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Cream)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProfileMenu("📍", "Mon atelier", "Marché central, Maroua")
            ProfileMenu("🧶", "Mes spécialités", "Boubous, robes, costumes")
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

@Preview(showBackground = true)
@Composable
private fun TelaAppPreview() {
    TelaApp()
}
