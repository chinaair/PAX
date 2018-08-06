package com.chinaair.util;

public class MoneyUtil {
	
	private static String moneyToText(String Number) {
		String sNumber = "";
		int len = Number.length();
		if (len == 1) {
			int iNu = Integer.parseInt("" + Number.charAt(0));
			sNumber += numberToTextA(iNu);
		} else if (len == 2) {
			int iChuc = Integer.parseInt("" + Number.charAt(0));
			int iDV = Integer.parseInt("" + Number.charAt(1));
			if (iChuc == 1) {
				if (iDV > 0) {
					sNumber += "Mười " + numberToTextA(iDV);
				} else {
					sNumber += "Mười ";
				}
			} else {
				sNumber += numberToTextA(iChuc) + " mươi ";
				if(iDV == 1) {
					sNumber += "mốt";
				} else if(iDV > 1) {
					sNumber += numberToTextA(iDV);
				}
			}
		} else if(!"000".equals(Number)){
			int iTram = Integer.parseInt("" + Number.charAt(0));
			int iChuc = Integer.parseInt("" + Number.charAt(1));
			int iDV = Integer.parseInt("" + Number.charAt(2));

			if (iChuc == 0) {
				if (iDV > 0) {
					sNumber += numberToTextA(iTram) + " trăm lẻ "
							+ numberToTextA(iDV);
				} else {
					sNumber += numberToTextA(iTram) + " trăm ";
				}
			} else if (iChuc == 1) {
				if (iDV > 0) {
					sNumber += numberToTextA(iTram) + " trăm mười "
							+ numberToTextA(iDV);
				} else {
					sNumber += numberToTextA(iTram) + " trăm mười ";
				}
			} else {
				if (iDV > 0) {
					sNumber += numberToTextA(iTram) + " trăm "
							+ numberToTextA(iChuc) + " mươi "
							+ numberToTextA(iDV);
				} else {
					sNumber += numberToTextA(iTram) + " trăm "
							+ numberToTextA(iChuc) + " mươi ";
				}
			}
		}
		return sNumber;
	}

	public static String tranlate(String sNumber) {

		String sR = "";
		String sR1 = "";
		String sR2 = "";
		String sR3 = "";
		String sR4 = "";
		// sR = ChuyenDV(sNumber);

		int seq = 0;
		int k = 1;
		for (int i = sNumber.length(); i >= 0; i--) {
			if (seq == 3) {
				String subStr = sNumber.substring(i, i + seq);
				if (k == 1) {
					sR = moneyToText(subStr) + " đồng";
				} else if (k == 2) {
					sR1 = moneyToText(subStr);
					if(!"000".equals(subStr)) {
						sR1 += " nghìn ";
					}
				} else if (k == 3) {
					sR2 = moneyToText(subStr);
					if(!"000".equals(subStr)) {
						sR2 += " triệu ";
					}
				} else {
					sR3 = moneyToText(subStr) + " tỷ ";
				}
				seq = 0;
				k++;
			}
			seq++;
		}
		if (seq > 1) {
			String subStr = sNumber.substring(0, seq - 1);
			if (k == 1) {
				sR = moneyToText(subStr) + " đồng";
			} else if (k == 2) {
				sR1 = moneyToText(subStr) + " nghìn ";
			} else if (k == 3) {
				sR2 = moneyToText(subStr) + " triệu ";
			} else {
				sR3 = moneyToText(subStr) + " tỷ ";
			}
		}
		// seq
		sR4 = sR3 + sR2 + sR1 + sR;

		return sR4;

	}
		
	private static String numberToTextA(int number) {
		String sR = "";
		switch (number) {
		case 0:
			sR = "không";
			break;
		case 1:
			sR = "một";
			break;
		case 2:
			sR = "hai";
			break;
		case 3:
			sR = "ba";
			break;
		case 4:
			sR = "bốn";
			break;
		case 5:
			sR = "năm";
			break;
		case 6:
			sR = "sáu";
			break;
		case 7:
			sR = "bảy";
			break;
		case 8:
			sR = "tám";
			break;
		case 9:
			sR = "chín";
			break;
		default:
			sR = "";
		}
		return sR;
	}

}
