package com.example.bismillahtestiprojectucp.ui.view.vendor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bismillahtestiprojectucp.R
import com.example.bismillahtestiprojectucp.model.Vendor
import com.example.bismillahtestiprojectucp.navigation.DestinasiNavigasi
import com.example.bismillahtestiprojectucp.ui.view.acara.OnError
import com.example.bismillahtestiprojectucp.ui.view.acara.OnLoading
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.HomeVendorViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.VendorPenyediaViewModel
import com.example.bismillahtestiprojectucp.ui.viewmodel.vendor.VendorUiState

object DestinasiVendorHome : DestinasiNavigasi {
    override val route = "vendor_home"
    override val titleRes = "Home Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendorHomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeVendorViewModel = viewModel(factory = VendorPenyediaViewModel.Factory)
) {

    LaunchedEffect(Unit) {
        viewModel.getVendor()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.primary)),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Daftar Vendor",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 35.sp
                ),
                color = Color.White,
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(110.dp),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(color = colorResource(R.color.yellow))
                .offset(y = -10.dp)
                .padding(top = 8.dp, bottom = 40.dp)
        ) {
            Column(modifier = Modifier) {
                VendorStatus(
                    vendorUiState = viewModel.vendorUiState,
                    retryAction = { viewModel.getVendor() },
                    modifier = Modifier,
                    onDetailClick = onDetailClick,
                )
            }

            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = RoundedCornerShape(20.dp),
                containerColor = colorResource(R.color.primary),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Vendor",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun VendorStatus(
    vendorUiState: VendorUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (vendorUiState) {
        is VendorUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is VendorUiState.Success -> {
            if (vendorUiState.vendor.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Vendor")
                }
            } else {
                VendorLayout(
                    vendor = vendorUiState.vendor,
                    modifier = modifier,
                    onDetailClick = { onDetailClick(it.idVendor) },
                )
            }
        }
        is VendorUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun VendorLayout(
    vendor: List<Vendor>,
    modifier: Modifier = Modifier,
    onDetailClick: (Vendor) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(vendor) { vendor ->
            VendorCard(
                vendor = vendor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(vendor) }
            )
        }
    }
}

@Composable
fun VendorCard(
    vendor: Vendor,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(6.dp)
            .shadow(8.dp, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.primary)
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.5f
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = vendor.namaVendor,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically // Baris untuk tanggal
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu, // Ikon tanggal
                            contentDescription = "Date Icon",
                            tint = Color.White, // Warna emas
                            modifier = Modifier.size(15.dp) // Ukuran ikon lebih kecil
                        )
                        Spacer(modifier = Modifier.width(5.dp)) // Jarak lebih kecil untuk elemen tanggal
                        Text(
                            text = vendor.idVendor,
                            style = MaterialTheme.typography.bodySmall, // Gaya teks lebih kecil
                            color = Color.White
                        )
                    }
                }

                Divider(
                    color = colorResource(R.color.yellow),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Location Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Jenis: ${vendor.jenisVendor}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Location Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Kontak: ${vendor.kontakVendor}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }


            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow Icon",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(15.dp)
                    .size(25.dp)
            )
        }
    }
}
