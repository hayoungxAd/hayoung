// This code is for analyzing path-to-purchase, which means which brand/sic users visited before they visit some brands, and where visited after those visit.
// Here, it tracks visitation for consecutive brand-brand, sic-sic, brand-sic, sic-brand AND also sees three consecutive visitation for some major brands.

package OneDay;

import java.io.*;
import java.util.*;
//import java.util.Map.Entry;

public class Oneday_for_1106_1112 {
	public static HashMap<String, ArrayList<String[]>> footTrafficMap = new HashMap<String, ArrayList<String[]>>();
	public static ArrayList<String> brandsList = new ArrayList<String>();
	public static ArrayList<String> sicsList = new ArrayList<String>();
	public static double[][] brandsVisitMat;
	public static double[][] sicsVisitMat;
	public static double[][] brandToSicVisitMat;
	public static double[][] sicToBrandVisitMat;
	public static HashMap<String, ArrayList<ArrayList<String>>> hourlyBrandVisitMap = new HashMap<String, ArrayList<ArrayList<String>>>();
	public static HashMap<String, Integer> McDonaldsThreeVisits ;
	public static HashMap<String, Integer> WalmartSupercenterThreeVisits ;
	public static HashMap<String, Integer> CvsThreeVisits ;
	public static HashMap<String, Integer> HomeDepotThreeVisits ;
	public static HashMap<String, Integer> TargetThreeVisits ;
	public static HashMap<String, Integer> ShellThreeVisits ;
	public static HashMap<String, Integer> MarriottThreeVisits ;
	public static HashMap<String, Integer> WalgreensThreeVisits ;
	public static HashMap<String, Integer> AmericanAirlinesThreeVisits ;
	public static HashMap<String, Integer> StarbucksThreeVisits ;
	public static HashMap<String, Integer> BurgerKingThreeVisits ;
	
	
	public static void main(String[] args) throws IOException {
        //collected visitation data (prox1, prox2) from avro file for 11/06/2016-11/12/2016
		String weeklyfile = "/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/dailydata/weeklymerged.txt";
		//Test file
		String filepath = "test1.txt";
		
		
		//test
//				readBrandsAndSics(filepath);
//				readFile(filepath);
//						brandsVisitMat = new double[brandsList.size() + 1][brandsList.size() + 1];
//		sicsVisitMat = new double[sicsList.size() + 1][sicsList.size() + 1];
//		brandToSicVisitMat = new double[brandsList.size()][sicsList.size()];
//		sicToBrandVisitMat = new double[sicsList.size()][brandsList.size()];
//		
//		McDonaldsThreeVisits = new HashMap<String, Integer>();
//				trackBrandsVisits();
//				trackSicsVisits();
//				trackBrandToSic();
//				trackSicToBrand();
//				threeBrandsVisit();
//		for (String key : hourlyBrandVisitMap.keySet()) {
//			System.out.println("id : " + key + hourlyBrandVisitMap.get(key).toString());
//		}
		
		//extract uid,hour,bid,sic,poi information for each row & collect list of the brands/sic in it
		readBrandsAndSics(weeklyfile);
		//readBrandsAndSics(filepath);
        
        //build matrix to store previous visitation and the next visitation information
        //brand to brand, sic to sic, brand to sic, sic to brand
		brandsVisitMat = new double[brandsList.size() + 1][brandsList.size() + 1];
		sicsVisitMat = new double[sicsList.size() + 1][sicsList.size() + 1];
		brandToSicVisitMat = new double[brandsList.size()][sicsList.size()];
		sicToBrandVisitMat = new double[sicsList.size()][brandsList.size()];
		
		//process daily data
		String basepath =  "/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/dailydata/";
		
		//test
		//String basepath = "/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/test/";

        //read each date's file and store the visitation path to above matrices.
		for (int i = 6 ; i <= 12; i++) {
			String dateStr;
			if (i < 10) {
				dateStr = "110" + Integer.toString(i) ;
			} else {
				dateStr = "11" + Integer.toString(i);
			}
			String dailyfile = basepath + dateStr + "/merged.txt";
			System.out.println("filepath :" + dailyfile);
			footTrafficMap = new HashMap<String, ArrayList<String[]>>();
			readFile(dailyfile);
			//readFile(filepath);
			trackBrandsVisits();
			trackSicsVisits();
			trackBrandToSic();
			trackSicToBrand();
			
			McDonaldsThreeVisits = new HashMap<String, Integer>();
			WalmartSupercenterThreeVisits = new HashMap<String, Integer>();
			CvsThreeVisits = new HashMap<String, Integer>();
			HomeDepotThreeVisits = new HashMap<String, Integer>();
			TargetThreeVisits = new HashMap<String, Integer>();
			ShellThreeVisits = new HashMap<String, Integer>();
			MarriottThreeVisits = new HashMap<String, Integer>();
			WalgreensThreeVisits = new HashMap<String, Integer>();
			AmericanAirlinesThreeVisits = new HashMap<String, Integer>();
			StarbucksThreeVisits = new HashMap<String, Integer>();
			BurgerKingThreeVisits= new HashMap<String, Integer>();
			
			//threeBrandsVisit();
			
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i +"/brand/McDonaldsThreeVisits.txt", McDonaldsThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/WalmartSupercenterThreeVisits.txt", WalmartSupercenterThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/CvsThreeVisits.txt", CvsThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/HomeDepotThreeVisits.txt", HomeDepotThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/TargetThreeVisits.txt", TargetThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/ShellThreeVisits.txt", ShellThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/MarriottThreeVisits.txt", MarriottThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/WalgreensThreeVisits.txt", WalgreensThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/AmericanAirlinesThreeVisits.txt", AmericanAirlinesThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/StarbucksThreeVisits.txt", StarbucksThreeVisits);
//			writeTxt("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/" + i + "/brand/BurgerKingThreeVisits.txt", BurgerKingThreeVisits);
//		
			System.out.println("finished :" + dateStr);
		}
		
		//System.out.println(sicsList);
//		for (int i = 0 ; i < sicsVisitMat.length; i++) {
//			System.out.print(i + "-th row : ");
//			for (int j = 0 ; j < sicsVisitMat[i].length; j++) {
//				if(j == sicsVisitMat[i].length - 1) {
//					System.out.print(sicsVisitMat[i][j]);
//				}
//				else {
//					System.out.print((int) sicsVisitMat[i][j] +", ");
//				}
//			}
//			System.out.println();
//		}
		
//		FileWriter fw = new FileWriter("/Users/hayoungseo/Desktop/others/path_to_purchase/checkoutput/brandsVisitMat.txt");
//		for (int i = 0; i < brandsVisitMat.length; i++) {
//			fw.write(i + ": " + Arrays.toString(brandsVisitMat[i]));
//			
//		}
//		fw.close();
		
//		writeList("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/brandsList.txt", brandsList);
//		writeList("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/sicsList.txt", sicsList);
//		
//		writeMatrix("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/brandsVisitMat.txt", brandsVisitMat);
//		writeMatrix("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/sicsVisitMat.txt", sicsVisitMat);
//		
//		writeMatrix("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/brandToSicVisitMat.txt", brandToSicVisitMat);
//		writeMatrix("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/sicToBrandVisitMat.txt", sicToBrandVisitMat);
//		//test
		
        
        //write matrices to text file
		writeList("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/brandsList.txt", brandsList);
		writeList("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/sicsList.txt", sicsList);
//		
		writeMatrix("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/brandsVisitMat.txt", brandsVisitMat);
		writeMatrix("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/sicsVisitMat.txt", sicsVisitMat);
//		
		writeMatrix("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/brandToSicVisitMat.txt", brandToSicVisitMat);
		writeMatrix("/Users/hayoungseo/Desktop/others/path_to_purchase/multipledays/1106_1112/output/sicToBrandVisitMat.txt", sicToBrandVisitMat);
		
		
	}
	
    //read each line from weekly data file and collect the list of brands/sic in it.
	public static void readBrandsAndSics(String filepath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			
			String line;
			line = br.readLine();
			while (line != null) {
				//System.out.println(line);
				if(line.split(",").length != 5) {
					System.out.println("element is not 5!!!");
					line = br.readLine();
					continue;
					//continue;
				}
				
				String uid = line.split(",")[0];
				String hour = line.split(",")[1].substring(11, 13);;
				String bid =  line.split(",")[2];
				String sic =  line.split(",")[3];
				String poi =  line.split(",")[4];
				
				//add bid to brandsList
				if (!brandsList.contains(bid)) {
					System.out.println("new brand to add :" + bid);
					for (int i = 0; i < brandsList.size(); i++) {
						int curBid = Integer.parseInt(brandsList.get(i)) ;
						//System.out.println(bid +": " + curBid);
						if (Integer.parseInt(bid) < curBid) {
							brandsList.add(i, bid);
							break;
						} else if (i == brandsList.size() - 1) {
							brandsList.add(bid);
							break;
						}
					}
					if (brandsList.isEmpty()) {
						brandsList.add(bid);
					}
				}
				
				//add sic to brandsList
				if (!sicsList.contains(sic)) {
					System.out.println("new sic to add :" + sic);
					for (int i = 0; i < sicsList.size(); i++) {
						int curSic = Integer.parseInt(sicsList.get(i)) ;
						//System.out.println(sic +": " + curSic);
						if (Integer.parseInt(sic) < curSic) {
							sicsList.add(i, sic);
							break;
						} else if (i == sicsList.size() - 1) {
							sicsList.add(sic);
							break;
						}
					}
					if (sicsList.isEmpty()) {
						sicsList.add(sic);
					}
				}
				
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

    //read files
	public static void readFile(String filepath) {
		BufferedReader br = null;
		int count = 0;
		try {
			br = new BufferedReader(new FileReader(filepath));
			
			String line;
			line = br.readLine();
			while (line != null) {
				//System.out.println("line :" + line);
				count ++;
				//System.out.println(line);
				//1. uid - arraylist of visit info
				trackUidFootTraffics(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		//for (Object key : footTrafficMap.keySet()) {
		//	System.out.println("id :" + key + " foot-traffics: " + footTrafficMap.get(key));
		//}
		//int count2 = 0;
//		for (Entry<String, ArrayList<String[]>> ee : footTrafficMap.entrySet()) {
//			String key = ee.getKey();
//			ArrayList<String[]> values = ee.getValue();
//			//System.out.println("id : " + key );
//			for (int i=0; i<values.size(); i++) {
//				//System.out.println("     visit " + i + "-th : " + Arrays.toString(values.get(i)));
//			}
//		}	
		//System.out.println(count + " : " + count2);
		
	}
	
    //store each uid's visitation ordered by hour.
	public static void trackUidFootTraffics(String line) {
		//String[] visit = line.split(",")[0];
		if(line.split(",").length != 5) {
			return;
		}
		
		String uid = line.split(",")[0];
		String hour = line.split(",")[1].substring(11, 13);;
		String bid =  line.split(",")[2];
		String sic =  line.split(",")[3];
		String poi =  line.split(",")[4];
		
		
		String[] visit = new String[]{hour, bid, sic, poi};
		
		if (!footTrafficMap.containsKey(uid)) {
			ArrayList<String[]> visitArrList = new ArrayList<String[]>();
			visitArrList.add(visit);
			footTrafficMap.put(uid, visitArrList);
		} else {
			ArrayList<String[]> visitArrList = (ArrayList<String[]>) footTrafficMap.get(uid);
			//int N = visitArrList.size();
			for (int i = 0; i < visitArrList.size(); i++) {
				String[] visits = visitArrList.get(i);
				int curHour = Integer.parseInt(visits[0]);
				if (Integer.parseInt(hour) < curHour) {
					visitArrList.add(i, visit);
					//System.out.println("present hour :" + hour + ", curHour" + curHour);
					break;
				} 
				else if (i == visitArrList.size() -1) {
					//System.out.println("Last : present hour :" + hour + ", curHour" + curHour);
					visitArrList.add(visit);
					break;
				}
			}
			footTrafficMap.put(uid, visitArrList);
		}	
	}
	
    //track 1-consecutive BRAND visit path(time ordered) and store it to visitation tracking matrices
	public static void trackBrandsVisits() {
		
		for (String key : footTrafficMap.keySet()) {
			ArrayList<String[]> visitsArr = footTrafficMap.get(key);
			int[] hoursFreq = new int[24];
			
			for (int i = 0; i < visitsArr.size(); i++) {
				String[] visit = visitsArr.get(i);
				int hr = Integer.parseInt(visit[0]);
				hoursFreq[hr] ++ ;
			}
			
			for (int i = 1; i <24; i++) {
				hoursFreq[i] = hoursFreq[i-1] + hoursFreq[i];
				//System.out.println(i + ":" + hoursFreq[i]);
			}
			
			String pastVisit ;
			
			for (int i = 0; i <24; i++) {
				ArrayList<String[]> visitsThisHour = new ArrayList<String[]>();
				ArrayList<String> brandVisitsThisHour = new ArrayList<String>();
				int startIdx;
				int endIdx;
				if (i == 0) {
					startIdx = 0;
					endIdx = hoursFreq[0];
				} else {
					startIdx = hoursFreq[i - 1];
					endIdx = hoursFreq[i];
				}
				
				if (startIdx != endIdx) {
					double weightedVisitCnt = 1.0;
					int visitCntThisHour = endIdx - startIdx;
					for (int idx = startIdx; idx < endIdx; idx++) {
						//String bid = visitsArr.get(idx)[1];
						visitsThisHour.add(visitsArr.get(idx));
						String bid = visitsArr.get(idx)[1];
						brandVisitsThisHour.add(bid);
					}
					if (startIdx == 0) {
						weightedVisitCnt = 1.0/((double)(endIdx - startIdx));
						for (int k = 0; k <brandVisitsThisHour.size(); k++) {
							String bid = brandVisitsThisHour.get(k);
							int brandIdx = brandsList.indexOf(bid);
							brandsVisitMat[brandsVisitMat.length-1][brandIdx] += weightedVisitCnt;
						}
					}
					if (endIdx == visitsArr.size()) {
						//last visits
						weightedVisitCnt = 1.0/((double)(endIdx - startIdx));
						for (int k = 0; k <brandVisitsThisHour.size(); k++) {
							String bid = brandVisitsThisHour.get(k);
							int brandIdx = brandsList.indexOf(bid);
							//System.out.println("last visit : "  + bid);
							brandsVisitMat[brandIdx][brandsVisitMat[0].length-1] += weightedVisitCnt;
						}
					}
					if (endIdx < visitsArr.size()) {
						//look at the next hours' visits
						ArrayList<String[]> visitsNextHour = new ArrayList<String[]>();
						ArrayList<String> brandVisitsNextHour = new ArrayList<String>();
						int j = i;
						while(true) {
							j = j + 1;
							if(hoursFreq[j] != hoursFreq[i]) {
								break;
							}
						}
						int nextStartIdx = hoursFreq[j - 1];
						int nextEndIdx = hoursFreq[j];
						for (int nextIdx = nextStartIdx; nextIdx < nextEndIdx; nextIdx++) {
							visitsNextHour.add(visitsArr.get(nextIdx)) ;
							String bid = visitsArr.get(nextIdx)[1];
							brandVisitsNextHour.add(bid);
						}
						int nextVisitCnt = brandVisitsNextHour.size();
						for (int n = 0 ; n < brandVisitsThisHour.size(); n ++) {
							String bidFrom = brandVisitsThisHour.get(n);
							int brandFromIdx = brandsList.indexOf(bidFrom);
							for (int m = 0; m < brandVisitsNextHour.size(); m++) {
								weightedVisitCnt = 1.0/((double)(endIdx - startIdx) * nextVisitCnt);
								String bidTo = brandVisitsNextHour.get(m);
								int brandToIdx = brandsList.indexOf(bidTo);
								//System.out.println(
										//"from : " + brandFromIdx + "to : " + brandToIdx +"(" + bidTo + ")");
								brandsVisitMat[brandFromIdx][brandToIdx] += weightedVisitCnt;
							}
						}
					}
				}
			}
		}
	}
    
    //track 1-consecutive SIC visit path(time ordered) and store it to visitation tracking matrices
	public static void trackSicsVisits() {
		
		
		for (String key : footTrafficMap.keySet()) {
			ArrayList<String[]> visitsArr = footTrafficMap.get(key);
			int[] hoursFreq = new int[24];
		
			for (int i = 0; i <visitsArr.size(); i++) {
				String[] visit = visitsArr.get(i);
				int hr = Integer.parseInt(visit[0]);
				hoursFreq[hr] ++;
			}
			
			for (int i = 1 ; i <24; i++) {
				hoursFreq[i] = hoursFreq[i-1] + hoursFreq[i];
			}
			
			for (int i = 0; i <24; i++) {
				ArrayList<String[]> visitsThisHour = new ArrayList<String[]>();
				ArrayList<String> sicVisitsThisHour = new ArrayList<String>();
				int startIdx;
				int endIdx;
				if (i == 0) {
					startIdx = 0;
					endIdx = hoursFreq[0];
				} else {
					startIdx = hoursFreq[i-1];
					endIdx = hoursFreq[i];
				}
				
				if (startIdx != endIdx) {
					double weightedVisitCnt = 1.0;
					//int visitCntThisHour = endIdx - startIdx;
					for (int idx = startIdx; idx < endIdx; idx++) {
						visitsThisHour.add(visitsArr.get(idx));
						String sic = visitsArr.get(idx)[2];
						sicVisitsThisHour.add(sic);
					}
					//sic might be duplicated because some brands share sic
					//Set<String> sicVisitsSet = new HashSet<>(); 
					//sicVisitsSet.addAll(sicVisitsThisHour);
					//sicVisitsThisHour.clear();
					//sicVisitsThisHour.addAll(sicVisitsSet);
					
					if (startIdx == 0) {
						//first visit
						weightedVisitCnt = 1.0/((double)(sicVisitsThisHour.size()));
						for (int k =0; k<sicVisitsThisHour.size() ; k++) {
							String sic = sicVisitsThisHour.get(k);
							int sicIdx = sicsList.indexOf(sic);
							sicsVisitMat[sicsVisitMat.length-1][sicIdx] += weightedVisitCnt;
						}
					}
					if (endIdx == visitsArr.size()) {
						//last visit
						weightedVisitCnt = 1.0/((double)(sicVisitsThisHour.size()));
						for (int k =0 ; k <sicVisitsThisHour.size(); k++) {
							String sic = sicVisitsThisHour.get(k);
							int sicIdx = sicsList.indexOf(sic);
							sicsVisitMat[sicIdx][sicsVisitMat[0].length-1] += weightedVisitCnt;
						}
					}
					if (endIdx < visitsArr.size()) {
						//look at the next hours' visits
						ArrayList<String[]> visitsNextHour = new ArrayList<String[]>();
						ArrayList<String> sicVisitsNextHour = new ArrayList<String>();
						int j = i;
						while (true) {
							j = j + 1;
							if (hoursFreq[j] != hoursFreq[i]) {
								break;
							}
						}
						int nextStartIdx = hoursFreq[j - 1];
						int nextEndIdx = hoursFreq[j];
						for (int nextIdx = nextStartIdx; nextIdx <nextEndIdx; nextIdx ++) {
							visitsNextHour.add(visitsArr.get(nextIdx));
							String sic = visitsArr.get(nextIdx)[2];
							sicVisitsNextHour.add(sic);
						}
						
						//Set<String> sicNextVisitsSet = new HashSet<>(); 
						//sicNextVisitsSet.addAll(sicVisitsNextHour);
						//sicVisitsNextHour.clear();
						//sicVisitsNextHour.addAll(sicNextVisitsSet);
						
						int nextVisitCnt = sicVisitsNextHour.size();
						for (int n = 0 ; n < sicVisitsThisHour.size(); n++) {
							String sicFrom = sicVisitsThisHour.get(n);
							int sicFromIdx = sicsList.indexOf(sicFrom);
							for (int m = 0; m <sicVisitsNextHour.size(); m++) {
								weightedVisitCnt = 1.0/((double)nextVisitCnt *sicVisitsThisHour.size());
								String sicTo = sicVisitsNextHour.get(m);
								int sicToIdx = sicsList.indexOf(sicTo);
								sicsVisitMat[sicFromIdx][sicToIdx] += weightedVisitCnt;
							}
						}
					}
				}
			}
		}	
	}
	
    // Track Brand to Sic consecutive visit
	public static void trackBrandToSic() {
		
		for (String key : footTrafficMap.keySet()) {
			ArrayList<String[]> visitsArr = footTrafficMap.get(key);
			int[] hoursFreq = new int[24];
			
			for (int i = 0 ; i < visitsArr.size(); i++) {
				String[] visit = visitsArr.get(i);
				int hr = Integer.parseInt(visit[0]);
				hoursFreq[hr] ++ ;
			}
			
			for (int i = 1; i < 24; i++) {
				hoursFreq[i] = hoursFreq[i-1] + hoursFreq[i];
			}
			
			String pastVisit ;
			
			for (int i = 0 ; i < 24 ; i++) {
				ArrayList<String[]> visitsThisHour = new ArrayList<String[]>();
				ArrayList<String> brandVisitsThisHour = new ArrayList<String>();
				int startIdx;
				int endIdx;
				if (i == 0) {
					startIdx = 0;
					endIdx = hoursFreq[0];
				} else {
					startIdx = hoursFreq[i-1];
					endIdx = hoursFreq[i];
				}
				
				if (startIdx != endIdx) {
					
					int visitCntThisHour = endIdx - startIdx;
					double weightedVisitCnt = 1.0/visitCntThisHour;
					//System.out.println("weightedVisitForThisHour" + weightedVisitCnt);
					
					for (int idx = startIdx; idx < endIdx; idx++) {
						visitsThisHour.add(visitsArr.get(idx));
						String bid = visitsArr.get(idx)[1];
						brandVisitsThisHour.add(bid);
					}
					
					
					
					//track visit before this brand visit??
					//track SIC visit after this brand visit
					if (endIdx == visitsArr.size()) {
						//nothing? don't care if it's last brand visit or not
						//System.out.println("this is the last visit");
					}
					else {
						//first matrix? 
						// if this is not a last visit.
						// check from this BID to next SIC
						
						for (int n = 0 ; n < brandVisitsThisHour.size(); n++) {
							String bidFrom = brandVisitsThisHour.get(n);
							int brandFromIdx = brandsList.indexOf(bidFrom);
							
							ArrayList<String[]> visitsNextHour = new ArrayList<String[]>();
							ArrayList<String> sicVisitsNextHour = new ArrayList<String>();
							int j = i ;
							while(true) {
								j = j + 1;
								if(hoursFreq[j] != hoursFreq[i]) {
									break;
								}
							}
							int nextStartIdx = hoursFreq[j-1];
							int nextEndIdx = hoursFreq[j];
							
							
							for (int nextIdx = nextStartIdx; nextIdx < nextEndIdx; nextIdx ++) {
								visitsNextHour.add(visitsArr.get(nextIdx));
								String bid = visitsArr.get(nextIdx)[1];
								if (!bid.equals(bidFrom)) {
									//System.out.println("bid from : " + bidFrom + " to bid : " + bid + " same? " + (bid!=bidFrom));
									String sic = visitsArr.get(nextIdx)[2];
									sicVisitsNextHour.add(sic);
								} else {
									
								}
							}
							
							double totalWeightedVisitCnt = weightedVisitCnt/ sicVisitsNextHour.size();
							//System.out.println("nextSicSize" + sicVisitsNextHour.size() + "TotalweightedVisitForthisBrandToSIC" + totalWeightedVisitCnt);
							
							for (int m = 0 ; m < sicVisitsNextHour.size(); m++) {
								String sicTo = sicVisitsNextHour.get(m);
								int sicToIdx = sicsList.indexOf(sicTo);
								//System.out.println(
									//	"from : " + brandFromIdx + "to : " + sicToIdx +"(" + sicTo + ")");
								brandToSicVisitMat[brandFromIdx][sicToIdx] += totalWeightedVisitCnt;
								
								
							}
						}
					}
					
//					//track visit from this brand to other SIC
//					if (startIdx == 0) {
//						//nothing? don't care if it's first brand visit or not
//					}
//					else {
//						//second matrix
//					}
					
				}
			}
		}
	}
	
    // Track Sic to Brand consecutive visit
	public static void trackSicToBrand() {
		
		for (String key : footTrafficMap.keySet()) {
			
			ArrayList<String[]> visitsArr = footTrafficMap.get(key);
			int[] hoursFreq = new int[24];
			
			for (int i = 0 ; i < visitsArr.size(); i++) {
				String[] visit = visitsArr.get(i);
				int hr = Integer.parseInt(visit[0]);
				hoursFreq[hr] ++ ;
			}
			
			for (int i = 1; i < 24; i ++) {
				hoursFreq[i] = hoursFreq[i-1] + hoursFreq[i];
			}
			
			String pastVisit;
			
			for (int i = 0 ; i < 24; i ++) {
				ArrayList<String[]> visitsThisHour = new ArrayList<String[]>();
				ArrayList<String> brandVisitsThisHour = new ArrayList<String>();
				ArrayList<String> sicVisitsThisHour = new ArrayList<String>();
				int startIdx;
				int endIdx;
				if (i == 0) {
					startIdx = 0;
					endIdx = hoursFreq[0];
				} else {
					startIdx = hoursFreq[i-1];
					endIdx = hoursFreq[i];
				}
				
				if (startIdx != endIdx) {
					int visitCntThisHour = endIdx - startIdx;
					double weightedVisitCnt = 1.0/(double)visitCntThisHour;
					//System.out.println("weightedVisitForThisHour" + weightedVisitCnt);
					
					for (int idx = startIdx; idx < endIdx; idx++) {
						visitsThisHour.add(visitsArr.get(idx));
						String bid = visitsArr.get(idx)[1];
						brandVisitsThisHour.add(bid);
						String sic = visitsArr.get(idx)[2];
						sicVisitsThisHour.add(sic);
					}
					
					//track brand visit after this SIC visit
					
					if (endIdx == visitsArr.size()) {
						//System.out.println("this is the last visit");
					}
					else {
						//check from this visit(sic) to the next brand visit
						
						for (int n = 0; n < sicVisitsThisHour.size(); n++) {
							String sicFrom = sicVisitsThisHour.get(n);
							String bidFrom = brandVisitsThisHour.get(n);
							int sicFromIdx = sicsList.indexOf(sicFrom);
						
						
							ArrayList<String[]> visitsNextHour = new ArrayList<String[]>();
							ArrayList<String> brandVisitsNextHour = new ArrayList<String>();
							int j = i ;
							while(true) {
								j = j + 1 ;
								if (hoursFreq[j] != hoursFreq[i]) {
									break;
								}
							}
							int nextStartIdx = hoursFreq[j-1];
							int nextEndIdx = hoursFreq[j];
							
							for (int nextIdx = nextStartIdx; nextIdx < nextEndIdx; nextIdx ++) {
								visitsNextHour.add(visitsArr.get(nextIdx));
								String bid = visitsArr.get(nextIdx)[1];
								if (!bid.equals(bidFrom)) {
									//String bid = visitsArr.get(nextIdx)[1];
									brandVisitsNextHour.add(bid);
								}                              
							}
							
							double totalWeightedVisitCnt = (double)weightedVisitCnt / (double)brandVisitsNextHour.size();
							//System.out.println("nextBrandSize " + brandVisitsNextHour.size() + " Total weighted Visit For this sic to brand : "+ totalWeightedVisitCnt);
							
							for (int m = 0; m < brandVisitsNextHour.size(); m++) {
								String brandTo = brandVisitsNextHour.get(m);
								int brandToIdx = brandsList.indexOf(brandTo);
								sicToBrandVisitMat[sicFromIdx][brandToIdx] += totalWeightedVisitCnt;
							}
						}
					}
				}
			}
		}
	}
	
    // Track Three consecutive brand visits (brand1 - brand2 - brand3) data sparse
	public static void threeBrandsVisit() {
		hourlyBrandVisitMap = new HashMap<String, ArrayList<ArrayList<String>>>();
		
		
		
		for (String key : footTrafficMap.keySet()) {
			if(footTrafficMap.get(key).size() >= 3 && footTrafficMap.get(key).size() <= 40) {
				ArrayList<ArrayList<String>> hourlyTrack = new ArrayList<ArrayList<String>>(24);
				
				ArrayList<String[]> visitsArr = footTrafficMap.get(key);
				
				for (int i =0 ; i < visitsArr.size(); i ++) {
					String[] visit = visitsArr.get(i);
					int hr = Integer.parseInt(visit[0]);
					String bid = visit[1];
					while(hourlyTrack.size() < 24) {
						hourlyTrack.add(new ArrayList<String>());
					}
					//System.out.println("size :" + hourlyTrack.size());
					if (hourlyTrack.get(hr).size() == 0) {
						ArrayList<String> thisHourVisitsArray = new ArrayList<String>();
						thisHourVisitsArray.add(bid);
						hourlyTrack.set(hr, thisHourVisitsArray);
					} else {
						ArrayList<String> thisHourVisitsArray = hourlyTrack.get(hr);
						thisHourVisitsArray.add(bid);
						hourlyTrack.set(hr, thisHourVisitsArray);
					}
				}
				hourlyBrandVisitMap.put(key, hourlyTrack);
			}
		}
		
		int count = 0;
		
		for (String key : hourlyBrandVisitMap.keySet()) {
			System.out.println("for uid : " + key);
			ArrayList<ArrayList<String>> hourlyTrack = hourlyBrandVisitMap.get(key);
			int firstHour = 0; 
			while(hourlyTrack.get(firstHour).size() == 0) {
				firstHour ++;
			}
			int lastHour = 23;
			while(hourlyTrack.get(lastHour).size() == 0) {
				lastHour --;
			}
			//System.out.println(firstHour + " " + lastHour);
			
			ArrayList<String> consecutiveVisitsList = new ArrayList<String>();
			Queue<String> q = new LinkedList<String>();
			int len = 0;
			for (int i = firstHour; i <= lastHour ; i++) {
				//System.out.println("i : " + i + " firstHour : " + firstHour + " lastHour : " + lastHour);
				if(hourlyTrack.get(i).size() != 0) {
					if( !q.isEmpty() ) {
						while(!q.isEmpty()) {
							String strlist = q.peek();
							//System.out.println("strList : " + strlist);
							//System.out.println("len : " + strlist.split("_").length);
							if (strlist.split("_").length > len) {
								break;
							}
							strlist = q.poll();
							for (int j = 0; j < hourlyTrack.get(i).size(); j++) {
								String updatedstrlist = strlist + "_" + hourlyTrack.get(i).get(j);
								q.offer(updatedstrlist);
								//System.out.println(updatedstrlist);
							}
						}
					len ++;
					} else if (q.isEmpty()) {
						len ++;
						String strlist = "";
						for (int j = 0; j < hourlyTrack.get(i).size(); j++) {
							//String strlist = "";
							strlist = hourlyTrack.get(i).get(j);
							q.offer(strlist);
							//System.out.println("strList : " + strlist);
						}		
					}
				}
			}
			
			//System.out.println("queue : ");
			ArrayList<String> thisUidsCandidatesList = new ArrayList<String>();
			while (!q.isEmpty()) {
				//System.out.println(q.peek());
				String strlist = q.poll();
				//System.out.println("len1 : " + strlist.split("_").length);
				if(strlist.split("_").length < 3) {
					//System.out.println("break");
					break;
				}
				String[] strarr = strlist.split("_");
				String newlist = strarr[0];
				for( int k = 1 ; k < strarr.length; k ++) {
					if(!strarr[k-1].equals(strarr[k])) {
						newlist += ("_" + strarr[k]);
					}
				}
				//System.out.println("updated : " + newlist);
				//System.out.println("len : " +newlist.split("_").length );
				
				if (newlist.split("_").length >=3) {
					for (int l = 0; l <= newlist.split("_").length -3; l++ ) {
						String candidate = newlist.split("_")[l] + "_" + newlist.split("_")[l+1] + "_" + newlist.split("_")[l+2];
						//System.out.println("candidate: " + candidate);
						if(!thisUidsCandidatesList.contains(candidate)) {
							thisUidsCandidatesList.add(candidate);
						}
						//
					}
				}
			}
			
			for (int k =0 ; k < thisUidsCandidatesList.size(); k++) {
				System.out.println("candidate to check: " + thisUidsCandidatesList.get(k));
				checkThisCandidateThreeBrandVisit(thisUidsCandidatesList.get(k));
			}
			System.out.println("percentage : " + (double)count / (double)hourlyBrandVisitMap.size());
			//System.out.println("end queue.");
			count ++;
		}
	}
	
    // For some najor brands, look at their previous/posterior visits
	public static void checkThisCandidateThreeBrandVisit(String candidate) {
		//System.out.println("candidate to check : " + candidate);
		int brand = Integer.parseInt(candidate.split("_")[1]);
		System.out.println("brand : " + brand);
		switch (brand) {
		
		case 25 :
			//addthisVisit(McDonaldsThreeVisits, candidate);
			if(McDonaldsThreeVisits.containsKey(candidate)) {
				McDonaldsThreeVisits.put(candidate, McDonaldsThreeVisits.get(candidate) + 1);
			} else {
				McDonaldsThreeVisits.put(candidate, 1);
			}
			break;
		case 282 :
			//addthisVisit(WalmartSupercenterThreeVisits, candidate);
			if(WalmartSupercenterThreeVisits.containsKey(candidate)) {
				WalmartSupercenterThreeVisits.put(candidate, WalmartSupercenterThreeVisits.get(candidate) + 1);
			} else {
				WalmartSupercenterThreeVisits.put(candidate, 1);
			}
			break;
		case 30 :
			//addthisVisit(CvsThreeVisits, candidate);
			if(CvsThreeVisits.containsKey(candidate)) {
				CvsThreeVisits.put(candidate, CvsThreeVisits.get(candidate) + 1);
			} else {
				CvsThreeVisits.put(candidate, 1);
			}
			break;
		case 8 :
			//addthisVisit(HomeDepotThreeVisits, candidate);
			if(HomeDepotThreeVisits.containsKey(candidate)) {
				HomeDepotThreeVisits.put(candidate, HomeDepotThreeVisits.get(candidate) + 1);
			} else {
				HomeDepotThreeVisits.put(candidate, 1);
			}
			break;
		case 10 :
			//addthisVisit(TargetThreeVisits, candidate);
			if(TargetThreeVisits.containsKey(candidate)) {
				TargetThreeVisits.put(candidate, TargetThreeVisits.get(candidate) + 1);
			} else {
				TargetThreeVisits.put(candidate, 1);
			}
			break;
		case 1424 :
			//addthisVisit(ShellThreeVisits, candidate);
			if(ShellThreeVisits.containsKey(candidate)) {
				ShellThreeVisits.put(candidate, ShellThreeVisits.get(candidate) + 1);
			} else {
				ShellThreeVisits.put(candidate, 1);
			}
			break;
		case 89 :
			//addthisVisit(MarriottThreeVisits, candidate);
			if(MarriottThreeVisits.containsKey(candidate)) {
				MarriottThreeVisits.put(candidate, MarriottThreeVisits.get(candidate) + 1);
			} else {
				MarriottThreeVisits.put(candidate, 1);
			}
			break;
		case 22 :
			//addthisVisit(WalgreensThreeVisits, candidate);
			if(WalgreensThreeVisits.containsKey(candidate)) {
				WalgreensThreeVisits.put(candidate, WalgreensThreeVisits.get(candidate) + 1);
			} else {
				WalgreensThreeVisits.put(candidate, 1);
			}
			break;
		case 4290 :
			//addthisVisit(AmericanAirlinesThreeVisits, candidate);
			if(AmericanAirlinesThreeVisits.containsKey(candidate)) {
				AmericanAirlinesThreeVisits.put(candidate, AmericanAirlinesThreeVisits.get(candidate) + 1);
			} else {
				AmericanAirlinesThreeVisits.put(candidate, 1);
			}
			break;
		case 24 :
			//addthisVisit(StarbucksThreeVisits, candidate);
			if(StarbucksThreeVisits.containsKey(candidate)) {
				StarbucksThreeVisits.put(candidate, StarbucksThreeVisits.get(candidate) + 1);
			} else {
				StarbucksThreeVisits.put(candidate, 1);
			}
			break;
		case 3 :
			//addthisVisit(BurgerKingThreeVisits, candidate);
			if(BurgerKingThreeVisits.containsKey(candidate)) {
				BurgerKingThreeVisits.put(candidate, BurgerKingThreeVisits.get(candidate) + 1);
			} else {
				BurgerKingThreeVisits.put(candidate, 1);
			}
			break;
		}
	}
	
	public static void addthisVisit(HashMap<String, Integer> brandMap, String candidate) {
		System.out.println("map name :");
		if(brandMap.containsKey(candidate)) {
			brandMap.put(candidate, brandMap.get(candidate) + 1);
		} else {
			brandMap.put(candidate, 1);
		}
		
	}
	
	
	public static void writeMatrix(String filename, double[][] matrix) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			
			for (int i = 0; i < matrix.length; i++) {
				String line = "";
				line += ("brand_" + i + ",") ;
				for (int j = 0 ; j <matrix[i].length; j++) {
					double d = matrix[i][j];
					Long L = Math.round(d);
					int val = Integer.valueOf(L.intValue());
					if(j == (matrix[i].length - 1)) {
						line += val;
					} else {
						line += (val + ",");
					}
				}
				bw.write(line);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeList(String filename, ArrayList<String> arrList) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			
			String line = "";
			for (int i = 0; i <arrList.size(); i++) {
				
				String bid = arrList.get(i);
				if (i == arrList.size() - 1) {
					line += (bid);
				}
				else {
					line += (bid + ",");
				}
			}
			bw.write(line);
			bw.newLine();
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeTxt(String filename, HashMap<String, Integer> brandMap) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			
			//String line = "";
			for (String key : brandMap.keySet()) {
				String line = "";
				line += key;
				line += ",";
				line += brandMap.get(key);
				bw.write(line);
				bw.newLine();
			}
			//bw.write(line);
			//bw.newLine();
			bw.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


