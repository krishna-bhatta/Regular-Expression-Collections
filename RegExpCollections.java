import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Regular expression pattern for following cases:
 * 
 *   #	email
 *   #	username
 *   #	password
 *   #	date 		yyyy/mm/dd
 *   #	12htime		10:45am
 *   #	24htime		23:59
 *   #	file		"jpg|png|gif|bmp"
 *   #	ipAddress
 *   #	htmlTag
 *   #	hexColor   
 *   
 */

class RegExpCollections {
	/*
	 * User given text
	 */
	private String inputString;
	
	/*
	 * Regular expression pattern
	 */
	private String regex;
	
	/*
	 * File extensions if required
	 */
	private String fileExt;
	
	/**
	 * Constructor for checking email, username, password, 12htime, 24htime, ipAddress, htmlTag, and hexColor
	 * @param method		type of regular expression that need to check.		e.g. "email"
	 * @param inputString	testing text 										e.g. "thebhatta@yahoo.com"
	 */
	public RegExpCollections(String method, String inputString) {
		this.setMethod(method);
		this.inputString = inputString;
	}
	
	/**
	 * Constructor for checking valid file extension
	 * @param method 	type of regular expression checking i.e. "file" 
	 * @param fileName	full name of file with extension	e.g. "my_photo.jpg"
	 * @param fileExt	required file extension				e.g. "jpg|png"
	 */
	public RegExpCollections( String method, String fileName, String fileExt) {
		this.fileExt = fileExt;
		this.setMethod(method);
		this.inputString = fileName;
	}

	/**
	 * Setting regular expression based on parameter passed by constructor
	 * @param method
	 */
	private void setMethod(String method) {
		
		switch (method) {
		
	case "email":
		/*
		 *  ^					# start of the line
		 *  [_A-Za-z0-9-]+		#  must start with string in the bracket [ ], must contains one or more (+)
		 *  (					# start of group #1
		 *  \\.[_A-Za-z0-9-]+	# follow by a dot "." and string in the bracket [ ], must contains one or more (+)
		 *  )*					# end of group #1, this group is optional (*)
		 *  @					# must contains a "@" symbol
		 *  [A-Za-z0-9]+       	# follow by string in the bracket [ ], must contains one or more (+)
		 *  (					# start of group #2 - first level TLD checking
		 *  \\.[A-Za-z0-9]+  	# follow by a dot "." and string in the bracket [ ], must contains one or more (+)
		 *  )*					# end of group #2, this group is optional (*)
		 *  (					# start of group #3 - second level TLD checking
		 *  \\.[A-Za-z]{2,}  	# follow by a dot "." and string in the bracket [ ], with minimum length of 2
		 *  )					# end of group #3
		 *	$					# end of the line
		 * 
		 */
		this.regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		break;
		case "username":
			/*
			 * ^			# Start of the line
			 * [a-z0-9_-]	# Match characters and symbols in the list, a-z, 0-9 , underscore , hyphen
			 * {3,15}		# Length at least 3 characters and maximum length of 15
			 * $			# End of the line
			*/
			this.regex = "^[a-z0-9_-]{5,15}$";
			break;
			
		case "password":
			
		/* (			# Start of group
		 * (?=.*\d)		# must contains one digit from 0-9
		 * (?=.*[a-z])	# must contains one lowercase characters
		 * (?=.*[A-Z])	# must contains one uppercase characters
		 * (?=.*[@#$%])	# must contains one special symbols in the list "@#$%"
		 * .			# match anything with previous condition checking
		 *  {6,20}		# length at least 6 characters and maximum of 20
		 *  )			# End of group
		 */
			this.regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";			
			break;
			
		case "date":
			/*
			 *  (				# start of group #1
			 *  0?[1-9]			# 01-09 or 1-9
			 *  |       	    # ..or
			 *  [12][0-9]		# 10-19 or 20-29
			 *  |				# ..or
			 *  3[01]			# 30, 31
			 *  ) 				# end of group #1
			 *  /				# follow by a "/"
			 *  (				# start of group #2
			 *  0?[1-9]			# 01-09 or 1-9
			 *  |				# ..or
			 *  1[012]			# 10,11,12
			 *  )				# end of group #2
			 *  /				# follow by a "/"
			 *  (				# start of group #3
			 *  (19|20)\\d\\d	# 19[0-9][0-9] or 20[0-9][0-9]
			 *  )				# end of group #3
			 */
			this.regex = "^((19|20)\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])$";
			break;
			
		case "12htime":
			/*
			 * (				# start of group #1
			 * 1[012]			# start with 10, 11, 12
			 * |				# or
			 * [1-9]			# start with 1,2,...9
			 * )				# end of group #1
			 * :				# follow by a semi colon (:)
			 * [0-5][0-9]		# follow by 0..5 and 0..9, which means 00 to 59
			 * (\\s)?			# follow by a white space (optional)
			 * (?i)				# next checking is case insensitive
			 * (am|pm)	# follow by am or pm
			 */
			this.regex = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
			break;
			
		case "24htime":
			/*
			 * (				# start of group #1
			 * [01]?[0-9]		# start with 0-9,1-9,00-09,10-19
			 * |				# or
			 * 2[0-3]			# start with 20-23
			 * )				# end of group #1
			 * :				# follow by semi coln (:)
			 * [0-5][0-9]		# follow by 0..5 and 0..9, which means 00 to 59
			 */
			this.regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
			break;
			
		case "file":
			/*
			 * * (		# Start of the group #1
			 * [^\s]+	# must contains one or more anything (except white space)
			 * (		# start of the group #2
			 * \.		# follow by a dot "."
			 * (?i)		# ignore the case sensitive checking
			 * (		# start of the group #3
			 * fielExt	# must contain at least one format of file
			 * )		# end of the group #3
			 * )		# end of the group #2
			 * $		# end of the string
			 * )		# end of the group #1
			 */
			this.regex = "([^\\s]+(\\.(?i)("+this.fileExt+"))$)";
			break;
			
		case "ipAddress":
			/*
			 * ^			# start of the line
			 * (			# start of group #1
			 * [01]?\\d\\d? # Can be one or two digits. If three digits appear, it must start either 0 or 1
			 * |			# ...or
			 * 2[0-4]\\d	# start with 2, follow by 0-4 and end with any digit (2[0-4][0-9])
			 * |           	# ...or
			 * 25[0-5]      # start with 2, follow by 5 and ends with 0-5 (25[0-5])
			 * )			# end of group #2
			 * \.           # follow by a dot "."
			 * ....         # repeat with 3 times (3x)
			 * $			# end of the line
			 */
			this.regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
			break;
			
		case "htmlTag":
			/*
			 * <	  	# start with opening tag "<"
			 * (		# start of group #1
			 * "[^"]*"	# only two double quotes are allow - "string"
			 * |		# ..or
			 * '[^']*'	# only two single quotes are allow - 'string'
			 * |		# ..or
			 * [^'">]	# cant contains one single quotes, double quotes and ">"
			 * )		# end of group #1
			 * *		# 0 or more
			 * 			# end with closing tag ">"
			 */
			this.regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
			break;
			
		case "hexColor":
			/*
			 *  ^				# start of the line
			 *  #				# must constains a "#" symbols
			 *  (				# start of group #1
			 *  [A-Fa-f0-9]{6}	# any strings in the list, with length of 6
			 *  |				# ..or
			 *  [A-Fa-f0-9]{3}	# any strings in the list, with length of 3
			 *  )				# end of group #1
			 *  $				#end of the line
			 */
			this.regex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
			break;

		default:
			break;
		}
	}
	
	/*
	 * Checking pattern given by constructor
	 * @return boolean
	 */
	public boolean result() {
		
		if(this.regex != null) {
			
			Pattern pattern = Pattern.compile(this.regex);
			Matcher matcher = pattern.matcher(this.inputString);
			
			if(matcher.find()) {
				return true;
			}
		}
		
		return false;
	}
}
