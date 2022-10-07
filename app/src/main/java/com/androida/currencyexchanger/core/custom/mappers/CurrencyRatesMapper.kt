package com.androida.currencyexchanger.core.custom.mappers

import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.data.models.remote.RatesModel

fun CurrencyRates.toRateAmount(rates: RatesModel): Double? {
    return when (this) {
        CurrencyRates.AED -> rates.aED
        CurrencyRates.AFN -> rates.aFN
        CurrencyRates.ALL -> rates.aLL
        CurrencyRates.AMD -> rates.aMD
        CurrencyRates.ANG -> rates.aNG
        CurrencyRates.AOA -> rates.aOA
        CurrencyRates.ARS -> rates.aRS
        CurrencyRates.AUD -> rates.aUD
        CurrencyRates.AWG -> rates.aWG
        CurrencyRates.AZN -> rates.aZN
        CurrencyRates.BAM -> rates.bAM
        CurrencyRates.BBD -> rates.bBD
        CurrencyRates.BDT -> rates.bDT
        CurrencyRates.BGN -> rates.bGN
        CurrencyRates.BHD -> rates.bHD
        CurrencyRates.BIF -> rates.bIF
        CurrencyRates.BMD -> rates.bMD
        CurrencyRates.BND -> rates.bND
        CurrencyRates.BOB -> rates.bOB
        CurrencyRates.BRL -> rates.bRL
        CurrencyRates.BSD -> rates.bSD
        CurrencyRates.BTC -> rates.bTC
        CurrencyRates.BTN -> rates.bTN
        CurrencyRates.BWP -> rates.bWP
        CurrencyRates.BYN -> rates.bYN
        CurrencyRates.BYR -> rates.bYR
        CurrencyRates.BZD -> rates.bZD
        CurrencyRates.CAD -> rates.cAD
        CurrencyRates.CDF -> rates.cDF
        CurrencyRates.CHF -> rates.cHF
        CurrencyRates.CLF -> rates.cLF
        CurrencyRates.CLP -> rates.cLP
        CurrencyRates.CNY -> rates.cNY
        CurrencyRates.COP -> rates.cOP
        CurrencyRates.CRC -> rates.cRC
        CurrencyRates.CUC -> rates.cUC
        CurrencyRates.CUP -> rates.cUP
        CurrencyRates.CVE -> rates.cVE
        CurrencyRates.CZK -> rates.cZK
        CurrencyRates.DJF -> rates.dJF
        CurrencyRates.DKK -> rates.dKK
        CurrencyRates.DOP -> rates.dOP
        CurrencyRates.DZD -> rates.dZD
        CurrencyRates.EGP -> rates.eGP
        CurrencyRates.ERN -> rates.eRN
        CurrencyRates.ETB -> rates.eTB
        CurrencyRates.EUR -> rates.eUR
        CurrencyRates.FJD -> rates.fJD
        CurrencyRates.FKP -> rates.fKP
        CurrencyRates.GBP -> rates.gBP
        CurrencyRates.GEL -> rates.gEL
        CurrencyRates.GGP -> rates.gGP
        CurrencyRates.GHS -> rates.gHS
        CurrencyRates.GIP -> rates.gIP
        CurrencyRates.GMD -> rates.gMD
        CurrencyRates.GNF -> rates.gNF
        CurrencyRates.GTQ -> rates.gTQ
        CurrencyRates.GYD -> rates.gYD
        CurrencyRates.HKD -> rates.hKD
        CurrencyRates.HNL -> rates.hNL
        CurrencyRates.HRK -> rates.hRK
        CurrencyRates.HTG -> rates.hTG
        CurrencyRates.HUF -> rates.hUF
        CurrencyRates.IDR -> rates.iDR
        CurrencyRates.ILS -> rates.iLS
        CurrencyRates.IMP -> rates.iMP
        CurrencyRates.INR -> rates.iNR
        CurrencyRates.IQD -> rates.iQD
        CurrencyRates.IRR -> rates.iRR
        CurrencyRates.ISK -> rates.iSK
        CurrencyRates.JEP -> rates.jEP
        CurrencyRates.JMD -> rates.jMD
        CurrencyRates.JOD -> rates.jOD
        CurrencyRates.JPY -> rates.jPY
        CurrencyRates.KES -> rates.kES
        CurrencyRates.KGS -> rates.kGS
        CurrencyRates.KHR -> rates.kHR
        CurrencyRates.KMF -> rates.kMF
        CurrencyRates.KPW -> rates.kPW
        CurrencyRates.KRW -> rates.kRW
        CurrencyRates.KWD -> rates.kWD
        CurrencyRates.KYD -> rates.kYD
        CurrencyRates.KZT -> rates.kZT
        CurrencyRates.LAK -> rates.lAK
        CurrencyRates.LBP -> rates.lBP
        CurrencyRates.LKR -> rates.lKR
        CurrencyRates.LRD -> rates.lRD
        CurrencyRates.LSL -> rates.lSL
        CurrencyRates.LTL -> rates.lTL
        CurrencyRates.LVL -> rates.lVL
        CurrencyRates.LYD -> rates.lYD
        CurrencyRates.MAD -> rates.mAD
        CurrencyRates.MDL -> rates.mDL
        CurrencyRates.MGA -> rates.mGA
        CurrencyRates.MKD -> rates.mKD
        CurrencyRates.MMK -> rates.mMK
        CurrencyRates.MNT -> rates.mNT
        CurrencyRates.MOP -> rates.mOP
        CurrencyRates.MRO -> rates.mRO
        CurrencyRates.MUR -> rates.mUR
        CurrencyRates.MVR -> rates.mVR
        CurrencyRates.MWK -> rates.mWK
        CurrencyRates.MXN -> rates.mXN
        CurrencyRates.MYR -> rates.mYR
        CurrencyRates.MZN -> rates.mZN
        CurrencyRates.NAD -> rates.nAD
        CurrencyRates.NGN -> rates.nGN
        CurrencyRates.NIO -> rates.nIO
        CurrencyRates.NOK -> rates.nOK
        CurrencyRates.NPR -> rates.nPR
        CurrencyRates.NZD -> rates.nZD
        CurrencyRates.OMR -> rates.oMR
        CurrencyRates.PAB -> rates.pAB
        CurrencyRates.PEN -> rates.pEN
        CurrencyRates.PGK -> rates.pGK
        CurrencyRates.PHP -> rates.pHP
        CurrencyRates.PKR -> rates.pKR
        CurrencyRates.PLN -> rates.pLN
        CurrencyRates.PYG -> rates.pYG
        CurrencyRates.QAR -> rates.qAR
        CurrencyRates.RON -> rates.rON
        CurrencyRates.RSD -> rates.rSD
        CurrencyRates.RUB -> rates.rUB
        CurrencyRates.RWF -> rates.rWF
        CurrencyRates.SAR -> rates.sAR
        CurrencyRates.SBD -> rates.sBD
        CurrencyRates.SCR -> rates.sCR
        CurrencyRates.SDG -> rates.sDG
        CurrencyRates.SEK -> rates.sEK
        CurrencyRates.SGD -> rates.sGD
        CurrencyRates.SHP -> rates.sHP
        CurrencyRates.SLL -> rates.sLL
        CurrencyRates.SOS -> rates.sOS
        CurrencyRates.SRD -> rates.sRD
        CurrencyRates.STD -> rates.sTD
        CurrencyRates.SVC -> rates.sVC
        CurrencyRates.SYP -> rates.sYP
        CurrencyRates.SZL -> rates.sZL
        CurrencyRates.THB -> rates.tHB
        CurrencyRates.TJS -> rates.tJS
        CurrencyRates.TMT -> rates.tMT
        CurrencyRates.TND -> rates.tND
        CurrencyRates.TOP -> rates.tOP
        CurrencyRates.TRY -> rates.tRY
        CurrencyRates.TTD -> rates.tTD
        CurrencyRates.TWD -> rates.tWD
        CurrencyRates.TZS -> rates.tZS
        CurrencyRates.UAH -> rates.uAH
        CurrencyRates.UGX -> rates.uGX
        CurrencyRates.USD -> rates.uSD
        CurrencyRates.UYU -> rates.uYU
        CurrencyRates.UZS -> rates.uZS
        CurrencyRates.VND -> rates.vND
        CurrencyRates.VUV -> rates.vUV
        CurrencyRates.WST -> rates.wST
        CurrencyRates.XAF -> rates.xAF
        CurrencyRates.XAG -> rates.xAG
        CurrencyRates.XAU -> rates.xAU
        CurrencyRates.XCD -> rates.xCD
        CurrencyRates.XDR -> rates.xDR
        CurrencyRates.XOF -> rates.xOF
        CurrencyRates.XPF -> rates.xPF
        CurrencyRates.YER -> rates.yER
        CurrencyRates.ZAR -> rates.zAR
        CurrencyRates.ZMK -> rates.zMK
        CurrencyRates.ZMW -> rates.zMW
        CurrencyRates.ZWL -> rates.zWL
    }
}
