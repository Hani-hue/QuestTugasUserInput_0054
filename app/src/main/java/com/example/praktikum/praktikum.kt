package com.example.praktikum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

//data class untuk menyimpan satu hasil biodata yang sudah di-submit
data class Biodata(
    val nama: String,
    val email: String,
    val tglLahir: String,
    val experience: String,
    val skill: String
)

@Composable
fun JudulForm() {
    Text(
        text = stringResource(id = R.string.judul_form),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.ungu_judul)
    )
    Text(
        text = stringResource(id = R.string.subjudul_form),
        style = MaterialTheme.typography.bodySmall,
        color = colorResource(id = R.color.abu_teks)
    )
}

@Composable
fun FieldNama(
    namaDepan: String,
    onNamaDepanChange: (String) -> Unit,
    namaBelakang: String,
    onNamaBelakangChange: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = namaDepan,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.weight(1f),
            label = { Text(text = stringResource(id = R.string.label_nama_depan)) },
            onValueChange = onNamaDepanChange
        )
        OutlinedTextField(
            value = namaBelakang,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.weight(1f),
            label = { Text(text = stringResource(id = R.string.label_nama_belakang)) },
            onValueChange = onNamaBelakangChange
        )
    }
}

@Composable
fun FieldEmail(
    email: String,
    onEmailChange: (String) -> Unit
) {
    OutlinedTextField(
        value = email,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.label_email)) },
        onValueChange = onEmailChange
    )
}

@Composable
fun FieldTglLahir(
    modifier: Modifier,
    tglLahir: String,
    onIkonClick: () -> Unit
) {
    OutlinedTextField(
        value = tglLahir,
        readOnly = true,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
        label = { Text(text = stringResource(id = R.string.label_tgl_lahir)) },
        trailingIcon = {
            IconButton(onClick = onIkonClick) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = stringResource(id = R.string.desc_ikon_tanggal)
                )
            }
        },
        onValueChange = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTanggal(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onTanggalDipilih: (String) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let {
                    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    format.timeZone = TimeZone.getTimeZone("UTC")
                    onTanggalDipilih(format.format(Date(it)))
                }
                onDismiss()
            }) { Text(stringResource(id = R.string.dialog_ok)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.dialog_batal))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}