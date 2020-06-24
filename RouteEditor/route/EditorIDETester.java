package route;

public class EditorIDETester {
  
	public static void main(String[] args) 
	{
		String rte1 = "KATL POUNC2.STEIT.MLU.LFK.J27.SAT J21 LRD J22 NLD UJ11 MTY UJ81 SLM KSLC";
		String rte2 = "JACCC2 KELLN Q56 KIWII WAVES CAPSS3";
		String rte3 = "JACCC2 DCT KELLN Q56 KIWII DCT WAVES CAPSS3";
		String rte4 = "JACCC2 DCT KELLN Q56 KIWII N13453893985 WAVES CAPSS3";
		String rte5 = "POUNC2.STEIT.MLU.LFK.J27.SAT J21 LRD J22 NLD UJ11 MTY UJ81 SLM";
		EditorIDETester frame = new EditorIDETester(rte5);
	}
  
	public EditorIDETester(String rte) 
	{
		System.out.println("Route being tested:  " + rte);
		String badRoute = rte;
		boolean issue = false;
        int timeOut = 0;
        badRoute.replaceAll(".", " ");
        while (badRoute.indexOf("DCT ") != -1 || timeOut >= 150) 
        {
          int index = badRoute.indexOf("DCT ");
          String beg = badRoute.substring(0, index);
          String end = "";
          if (index + 4 < badRoute.length())
          {
            end = badRoute.substring(index + 4); 
          }
          badRoute = String.valueOf(beg) + end;
          timeOut++;
        } 
	    if (badRoute.substring(badRoute.length() - 3).equals("DCT"))
	    {
	    	badRoute = badRoute.substring(0, badRoute.length() - 3); 
	    }
	    if (timeOut >= 150)
	    {
	    	issue = true; 
	    }
	    timeOut = 0;
	    while (badRoute.indexOf("/") != -1 || timeOut >= 150) 
	    {
	          int index = badRoute.indexOf("/");
	          String beg = badRoute.substring(0, index);
	          String end = "";
	          int count = index + 1;
	          for (int i = index + 1; i < badRoute.length(); i++) 
	          {
	        	  if (badRoute.charAt(i) == ' ') 
	        	  {
	        		  count = i;
	        		  break;
	        	  } 
	          } 
	          if (count < badRoute.length())
	          {
	        	  end = badRoute.substring(count); 
	          }
	          badRoute = String.valueOf(beg) + end;
	          timeOut++;
	        } 
	        if (badRoute.charAt(0) == 'N' && Character.isDigit(badRoute.charAt(1))) 
	        {
	              int ind = 1;
	              timeOut = 0;
	              while (badRoute.charAt(ind) != ' ' || timeOut >= 150) 
	              {
	            	  ind++;
	            	  timeOut++;
	              } 
	              badRoute = badRoute.substring(ind + 1);
	        } 
	        if (timeOut >= 150)
	        {
	        	issue = true;
	            timeOut = 0;
	        }
	        while (badRoute.indexOf('.') != -1 && timeOut < 150) 
	        {
	        	badRoute = badRoute.replace(".", " ");
	            timeOut++;
	            System.out.println("Fuck");
	        } 
	        if (timeOut >= 150)
	        {
	        	System.out.println("wtf");
	        	issue = true; 
	        }
	        	
	        if (issue) 
	        {
	        	System.out.println("Error Occured during parsing");
	        } 
	        else 
	        {
	        	System.out.println(badRoute);
	        }
	}
        
}