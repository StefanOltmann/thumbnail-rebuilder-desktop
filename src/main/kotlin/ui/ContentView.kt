/*
 * Thumbnail Rebuilder
 * Copyright (C) 2024 Stefan Oltmann
 * https://stefan-oltmann.de/thumbnail-rebuilder-desktop
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import java.io.File

@Composable
fun ContentView() {

    var files by remember { mutableStateOf( emptyList<File>() ) }

    val onFilesImport: (List<String>) -> Unit = {

        val tempFiles = mutableListOf<File>()

        for (path in it) {

            println("Import: $path")

            val file = File(path)

            if (file.isFile)
                tempFiles.add(file)
        }

        files = tempFiles.toList()
    }

    if (files.isEmpty()) {

        ImportScreen(onFilesImport)

    } else {

        LazyColumn {

            items(files) { file ->

                Text(
                    text = file.path,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
