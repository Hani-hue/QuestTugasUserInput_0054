
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

@Composable
fun DropdownExperience(
    modifier: Modifier,
    experience: String,
    pilihan: List<String>,
    onPilih: (String) -> Unit
) {
    var tampilExperience by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = experience,
            readOnly = true,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.label_experience)) },
            trailingIcon = {
                IconButton(onClick = { tampilExperience = true }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.desc_ikon_dropdown)
                    )
                }
            },
            onValueChange = {}
        )
        DropdownMenu(
            expanded = tampilExperience,
            onDismissRequest = { tampilExperience = false }
        ) {
            pilihan.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onPilih(item)
                        tampilExperience = false
                    }
                )
            }
        }
    }
}

@Composable
fun PilihSkill(
    daftarSkill: List<String>,
    skillTerpilih: MutableList<String>
) {
    Text(
        text = stringResource(id = R.string.label_skill),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daftarSkill.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = skillTerpilih.contains(item),
                    onCheckedChange = {
                        if (it) skillTerpilih.add(item) else skillTerpilih.remove(item)
                    }
                )
                Text(text = item, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun TombolProceed(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(fraction = 1f),
        //the button is enabled when all fields are filled
        enabled = enabled,
        onClick = onClick
    ) {
        Text(stringResource(id = R.string.submit))
    }
}

@Composable
fun PembatasForm() {
    HorizontalDivider(
        modifier = Modifier.padding(
            bottom = dimensionResource(id = R.dimen.padding_medium),
            top = dimensionResource(id = R.dimen.padding_medium)
        ),
        thickness = dimensionResource(id = R.dimen.divider_tipis),
        color = colorResource(id = R.color.abu_tua)
    )
}

@Composable
fun CardHasil(item: Biodata) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.black)),
        modifier = Modifier.width(300.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 5.dp, vertical = 15.dp)) {
            Text(text = stringResource(id = R.string.hasil_nama, item.nama), color = colorResource(id = R.color.white))
            Text(text = stringResource(id = R.string.hasil_email, item.email), color = colorResource(id = R.color.white))
            Text(text = stringResource(id = R.string.hasil_tgl_lahir, item.tglLahir), color = colorResource(id = R.color.white))
            Text(text = stringResource(id = R.string.hasil_experience, item.experience), color = colorResource(id = R.color.white))
            Text(text = stringResource(id = R.string.hasil_skill, item.skill), color = colorResource(id = R.color.white))
        }
    }
}

//card dibuat dinamis: jumlah card mengikuti jumlah data di daftarHasil
@Composable
fun DaftarHasil(daftarHasil: List<Biodata>) {
    daftarHasil.forEach { item ->
        CardHasil(item = item)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDataDiri(modifier: Modifier){
    //variabel-variabel untuk mengingat nilai masukan dari keyboard
    var textNamaDepan by remember { mutableStateOf("") }
    var textNamaBelakang by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    var textTglLahir by remember { mutableStateOf("") }
    var textExperience by remember { mutableStateOf("") }
    val skillTerpilih = remember { mutableStateListOf<String>() }

    //daftar pilihan diambil dari resources (dinamis)
    val experience: List<String> = stringArrayResource(id = R.array.experience_options).toList()
    val skill: List<String> = stringArrayResource(id = R.array.skill_options).toList()

    //variabel untuk dialog tanggal
    var tampilTanggal by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    //variabel untuk menyimpan data yang diperoleh dari komponen UI
    val daftarHasil = remember { mutableStateListOf<Biodata>() }

    if (tampilTanggal) {
        DialogTanggal(
            datePickerState = datePickerState,
            onDismiss = { tampilTanggal = false },
            onTanggalDipilih = { textTglLahir = it }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JudulForm()

        FieldNama(
            namaDepan = textNamaDepan,
            onNamaDepanChange = { textNamaDepan = it },
            namaBelakang = textNamaBelakang,
            onNamaBelakangChange = { textNamaBelakang = it }
        )

        FieldEmail(
            email = textEmail,
            onEmailChange = { textEmail = it }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FieldTglLahir(
                modifier = Modifier.weight(1f),
                tglLahir = textTglLahir,
                onIkonClick = { tampilTanggal = true }
            )
            DropdownExperience(
                modifier = Modifier.weight(1f),
                experience = textExperience,
                pilihan = experience,
                onPilih = { textExperience = it }
            )
        }

        PilihSkill(
            daftarSkill = skill,
            skillTerpilih = skillTerpilih
        )

        TombolProceed(
            enabled = textNamaDepan.isNotEmpty() && textNamaBelakang.isNotEmpty() &&
                    textEmail.isNotEmpty() && textTglLahir.isNotEmpty() &&
                    textExperience.isNotEmpty() && skillTerpilih.isNotEmpty(),
            onClick = {
                daftarHasil.add(
                    Biodata(
                        nama = textNamaDepan + " " + textNamaBelakang,
                        email = textEmail,
                        tglLahir = textTglLahir,
                        experience = textExperience,
                        skill = skillTerpilih.joinToString(", ")
                    )
                )
                textNamaDepan = ""
                textNamaBelakang = ""
                textEmail = ""
                textTglLahir = ""
                textExperience = ""
                skillTerpilih.clear()
            }
        )

        PembatasForm()

        DaftarHasil(daftarHasil = daftarHasil)
    }
}
