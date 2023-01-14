/* You are given an integer n... There are n rooms numbered from 0 to n - 1... You are given a 2D integer array meetings where meetings[i] = [starti, endi] means that a meeting will be held during the half-closed time interval [starti, endi]... All the values of starti are unique... Meetings are allocated to rooms in the following manner:
* Each meeting will take place in the unused room with the lowest number...
* If there are no available rooms, the meeting will be delayed until a room becomes free... The delayed meeting should have the same duration as the original meeting...
* When a room becomes unused, meetings that have an earlier original start time should be given the room...
Return the number of the room that held the most meetings... If there are multiple rooms, return the room with the lowest number... A half-closed interval [a, b] is the interval between a and b including a and not including b... 
   * Eg 1:  n = 2        meetings = [[1,5],[2,7],[0,10],[3,4]]                   Output = 0
   * Eg 2:  n = 3        meetings = [[1,20],[3,5],[6,8],[2,10],[4,9]]            Output = 1
   * Eg 3:  n = 4        meetings = [[18,19],[3,12],[17,19],[2,13],[7,10]]       Output = 2      
*/
import java.util.*;
public class Meetings
{
    public int MaximumMeetingRoom(int meet[][], int n)
    {
        if(meet.length == 1)
            return 0;
        Arrays.sort(meet, (a,b) -> Integer.compare(a[0], b[0]));   // Sorting 2d array in ascending order...
        PriorityQueue<Integer> avail = new PriorityQueue<Integer>();    // Storing the room numbers, lower room number gets higher priority...
        PriorityQueue<int[]> endtime = new PriorityQueue<int[]>((a,b) -> Integer.compare(a[1], b[1]));   // Lambda Comparator function... 1 represents the row, since we are aligning by (row, end time), a, b values represent ascending and descending order... Lower End time gets higher priority...
        for(int i = 0; i < n ; i++)
            avail.add(i);    // Adding the indices of the Rooms, since all rooms will be available in starting...
        int room = 0;
        int meeting[] = new int[n];    // Array for storing the number of meetings taken place...
        for(int i = 0; i < meet.length; i++)
        {
            if(!avail.isEmpty())    // While a room is available for meeting...
            {
                room = avail.poll();     // Call the room with lowest room number...
                meeting[room] = meeting[room] + 1;     // Incrementing the number of meetings held in that room...
                int arr[] = new int[2];    // Array for Storing data...
                arr[0] = room;
                arr[1] = meet[i][1];
                endtime.add(arr);     // Adding the Meeting to the Priority Queue for Scheduling...
            }
            else if(avail.isEmpty())    // If no rooms are available...
            {
                int data[] = new int[2];
                data = endtime.poll();     // The room having lowest meeting end time will be used...
                int time = data[1];
                room = data[0];       // Values initialized...
                meeting[data[0]] = meeting[data[0]] + 1;    // The meeting for that room incremented...
                data[1] = meet[i][1] + time;     // the new time will be the time elapsed by the previous meeting and the new meeting ongoing...
                endtime.add(data);     // Adding the new meeting...
            }
        }
        int maxMeetings = 0, roomNumber = 0;
        for(int i = 0; i < n; i++)
            System.out.println("Room Number : "+i+" Number of Meetings : "+meeting[i]);
        for(int i = n-1; i >= 0; i--)
        {
            if(maxMeetings <= meeting[i])
            {
                maxMeetings = meeting[i];
                roomNumber = i;     // Getting the lowest room number with maximum Meetings...
            }
        }
        return roomNumber;
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int x;
        System.out.print("Enter the number of meetings to be held : ");
        x = sc.nextInt();
        int meet[][] = new int[x][2];
        for(int i = 0; i < x; i ++)
        {
            System.out.print("Enter "+(i+1)+" th meeting Start time : ");
            meet[i][0] = sc.nextInt();
            System.out.print("Enter "+(i+1)+" th meeting End time : ");
            meet[i][1] = sc.nextInt();
        }
        System.out.print("Enter the number of Meeting rooms : ");
        x = sc.nextInt();
        Meetings meetings = new Meetings();     // Object creation...
        System.out.println("The Room with Maximum Meetings is : "+meetings.MaximumMeetingRoom(meet, x));
        sc.close();
    }
}

// Time Complexity  - O(m log m) time...           m = length of the meeting array...
// Space Complexity - O(n) time...

/* DEDUCTIONS :- 
 * 1. Since we want to use the minimum room number and minimum end time possible over any given point of time in the problem, so we can use two Min Heaps each for room number and end time...
 * 2. Now whenever no room is available we use the Min Heap of end time, otherwise we use the Min Heap of room number to perform operations...
 * 3. We also keep a check of the number of meetings performed in a given room by the help of counters and array... 
*/