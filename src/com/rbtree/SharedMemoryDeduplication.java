package com.rbtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SharedMemoryDeduplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		redBlackTree<Integer, Integer> unstable_tree = new redBlackTree<Integer, Integer>();
		redBlackTree<Integer, Integer> stable_tree = new redBlackTree<Integer, Integer>();
		Scanner scan = new Scanner(System.in);
		Integer found;
		int pId, pHash;
		List<Integer> memory = new ArrayList<Integer>();
		redBlackTreeNode<Integer, Integer> temp;
		int option = 1;

		while (option != 2) {
			int stepNo = 0;
			System.out.println("\nEnter operation \n1.Load \n2.Exit\n");
			System.out.print("Enter option:");
			option = scan.nextInt();
			switch (option) {
			case 1:
				System.out.println("Enter page Id: ");
				pId = scan.nextInt();
				System.out.println("Enter hash of the page: ");
				pHash = scan.nextInt();
				// ------------------ for test purpose -------
				// int size=3000;
				// Random generator = new Random();
				// int[] a = new int[size];
				// for (int i =0; i<size; i++)
				// {
				// a[i]= generator.nextInt(size+1000);
				// }
				//
				// double start = System.nanoTime(); //start
				// for(int i = 0; i<size;i++){ // for test
				// int pId = a[i];
				// int pHash = a[i]+1;
				System.out.println("\n<Steps performed:>");
				stepNo++;
				System.out.println(stepNo + " <Searching page in stable tree>");
				if ((found = stable_tree.search(pId)) != null) {
					stepNo++;
					System.out.println(stepNo
							+ " <Page ID found in stable tree>");
					temp = stable_tree.update(pId);
					int tval = Integer.valueOf(temp.value);
					int tupdate = tval + pHash;
					stepNo++;
					System.out.println(stepNo + " <Updating Hash from " + tval
							+ " to " + tupdate + ">");
					temp.value = (Integer) tupdate;
					// System.out.println("temp.value: " + temp.value);
				} else {
					stepNo++;
					System.out.println(stepNo
							+ " <Page ID not found in Stable tree>");
					stepNo++;
					System.out.println(stepNo
							+ " <Searching page in Unstable tree>");
					if ((found = unstable_tree.search(pId)) == null) {
						stepNo++;
						System.out.println(stepNo
								+ " <Page ID not found in unstable tree>");
						stepNo++;
						System.out.println(stepNo
								+ " <Inserting page in Unstable tree>");
						unstable_tree.insert(pId, pHash);
					} else {
						stepNo++;
						System.out.println(stepNo
								+ " <Page ID found in unstable tree>");
						temp = unstable_tree.update(pId);
						int tval = Integer.valueOf(temp.value);
						int tupdate = tval + pHash;
						stepNo++;
						System.out.println(stepNo + " <Updating Hash of "
								+ temp.value + " to " + tupdate + ">");
						temp.value = (Integer) tupdate;
						// System.out.println("temp.value: " + temp.value);
						unstable_tree.delete(pId);
						stepNo++;
						System.out.println(stepNo
								+ " <Moving page to stable tree>");
						stable_tree.insert(pId, temp.value);
					}
				}
				// }// end of test
				// double end = System.nanoTime();// end
				// double diff = end - start;//
				// System.out.println(end);
				// printing tree in Inorder
				System.out.println("========================");
				System.out.println("Unstable tree:");
				unstable_tree.inOrderPrint();
				System.out.println("\nStable tree:");
				stable_tree.inOrderPrint();
				System.out.println("\n========================");
				break;
			case 2:
				System.out.println("Program ended");
				break;
			}
		}
	}

}
