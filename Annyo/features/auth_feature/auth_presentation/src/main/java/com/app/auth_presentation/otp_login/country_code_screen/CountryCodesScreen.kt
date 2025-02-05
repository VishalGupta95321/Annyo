package com.app.auth_presentation.otp_login.country_code_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.auth_presentation.otp_login.country_code_screen.utils.CountryCode
import com.example.ui.StandardTextField

@Composable
fun CountryCodesScreen(
    modifier: Modifier = Modifier,
    viewModel: CountryCodeViewModel = hiltViewModel(),
    onCountryCodeSelected:(CountryCode)->Unit
) {
    val query = viewModel.searchQuery

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x88615757)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(0.8f)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20.dp),
                )
        ) {
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = query,
                singleLine = true,
                onValueChange = viewModel::onQueryChange,
                placeholderText = "Search Your Country",
            )
            CountryCodesList(
               countryCode = CountryCode.values()
                   .filter { it.toString().contains(query,true)}
            ) { onCountryCodeSelected(it) }
        }
    }
}


@Composable
fun CountryCodeListItem(
    countryCode: CountryCode,
    onCountryCodeSelected:()->Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onCountryCodeSelected() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.titleMedium,
            text = countryCode.countryFlag+countryCode.countryName
        )
        Text(
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.titleMedium,
            text = countryCode.countryCode
        )
    }
}  /// todo make a standard list item


@Composable
fun CountryCodesList(
    modifier: Modifier = Modifier,
    countryCode : List<CountryCode>,
    onCountryCodeSelected: (CountryCode)->Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .then(modifier)
    ) {
        items(countryCode){ countryCode ->
            CountryCodeListItem(
                countryCode
            ){onCountryCodeSelected(countryCode)}
        }
    }
}

/// todo &*(6&^%(&^%&&*

