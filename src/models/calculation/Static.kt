package de.alxgrk.models.calculation

import de.alxgrk.routing.error.UnsupportedStandardstufeException
import models.table.Art
import models.table.Dach
import models.table.Geschosse
import kotlin.Array
import kotlin.Int
import kotlin.arrayOf as a

fun nutzungsdauerNachStandardstufe(id: Int, standardstufe: Int) = when(standardstufe) {
    1 -> 60
    2 -> 65
    3 -> 70
    4 -> 75
    5 -> 80
    else -> throw UnsupportedStandardstufeException(id.toString(), standardstufe)
}

// TODO catch ArrayIndexOutOfBoundsException
fun kostenkennwertFor(geschosse: Geschosse, dach: Dach, art: Art, standardstufe: Int): Int =
    KostenkennwertMatrix[geschosse.ordinal][dach.ordinal][art.toKennwertMatrixPosition()][standardstufe - 1]

val KostenkennwertMatrix: Array<Array<Array<Array<Int>>>> = a(
    geschosse_kgeg(
        dach_a(
            art_eh(655, 725, 835, 1005, 1260),
            art_dr(615, 685, 785, 945, 1180),
            art_rm(575, 640, 735, 885, 1105)
        ),
        dach_na(
            art_eh(545, 605, 695, 840, 1050),
            art_dr(515, 570, 655, 790, 985),
            art_rm(480, 535, 615, 740, 925)
        ),
        dach_f(
            art_eh(705, 785, 900, 1085, 1360),
            art_dr(665, 735, 845, 1020, 1275),
            art_rm(620, 690, 795, 955, 1195)
        )
    ),
    geschosse_kgegog(
        dach_a(
            art_eh(655, 725, 935, 1005, 1260),
            art_dr(615, 685, 785, 945, 1180),
            art_rm(575, 640, 735, 885, 1105)
        ),
        dach_na(
            art_eh(570, 635, 730, 880, 1100),
            art_dr(535, 595, 685, 825, 1035),
            art_rm(505, 560, 640, 775, 965)
        ),
        dach_f(
            art_eh(665, 740, 850, 1025, 1285),
            art_dr(625, 695, 800, 965, 1205),
            art_rm(585, 650, 750, 905, 1130)
        )
    ),
    geschosse_eg(
        dach_a(
            art_eh(790, 875, 1005, 1215, 1515),
            art_dr(740, 825, 945, 1140, 1425),
            art_rm(695, 770, 885, 1065, 1335)
        ),
        dach_na(
            art_eh(585, 650, 745, 900, 1125),
            art_dr(550, 610, 700, 845, 1055),
            art_rm(515, 570, 655, 790, 990)
        ),
        dach_f(
            art_eh(920, 1025, 1180, 1420, 1775),
            art_dr(865, 965, 1105, 1335, 1670),
            art_rm(810, 900, 1035, 1250, 1560)
        )
    ),
    geschosse_egog(
        dach_a(
            art_eh(720, 800, 920, 1105, 1385),
            art_dr(675, 750, 865, 1040, 1300),
            art_rm(635, 705, 810, 975, 1215)
        ),
        dach_na(
            art_eh(620, 690, 790, 955, 1190),
            art_dr(580, 645, 745, 895, 1120),
            art_rm(545, 605, 695, 840, 1050)
        ),
        dach_f(
            art_eh(785, 870, 1000, 1205, 1510),
            art_dr(735, 820, 940, 1135, 1415),
            art_rm(690, 765, 880, 1060, 1325)
        )
    )
)


private fun geschosse_kgeg(ausgebaut: Array<Array<Int>>, nichtAusgebaut: Array<Array<Int>>, flach: Array<Array<Int>>) =
    a(ausgebaut, nichtAusgebaut, flach)

private fun geschosse_kgegog(
    ausgebaut: Array<Array<Int>>,
    nichtAusgebaut: Array<Array<Int>>,
    flach: Array<Array<Int>>
) = a(ausgebaut, nichtAusgebaut, flach)

private fun geschosse_eg(ausgebaut: Array<Array<Int>>, nichtAusgebaut: Array<Array<Int>>, flach: Array<Array<Int>>) =
    a(ausgebaut, nichtAusgebaut, flach)

private fun geschosse_egog(ausgebaut: Array<Array<Int>>, nichtAusgebaut: Array<Array<Int>>, flach: Array<Array<Int>>) =
    a(ausgebaut, nichtAusgebaut, flach)


private fun dach_a(eh: Array<Int>, dr: Array<Int>, rm: Array<Int>) = a(eh, dr, rm)
private fun dach_na(eh: Array<Int>, dr: Array<Int>, rm: Array<Int>) = a(eh, dr, rm)
private fun dach_f(eh: Array<Int>, dr: Array<Int>, rm: Array<Int>) = a(eh, dr, rm)

private fun art_eh(s1: Int, s2: Int, s3: Int, s4: Int, s5: Int) = a(s1, s2, s3, s4, s5)
private fun art_dr(s1: Int, s2: Int, s3: Int, s4: Int, s5: Int) = a(s1, s2, s3, s4, s5)
private fun art_rm(s1: Int, s2: Int, s3: Int, s4: Int, s5: Int) = a(s1, s2, s3, s4, s5)

private fun Art.toKennwertMatrixPosition() = when (this) {
    Art.EIN -> 0
    Art.DOPPEL -> 1
    Art.REIHENEND -> 1
    Art.REIHE -> 2
    Art.REIHENMITTEL -> 2
}