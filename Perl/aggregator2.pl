#!/usr/bin/env perl
#===============================================================================
#
#  FILE:        aggregator.pl
#
#  USAGE:       ./aggregator.pl <feeds text> <outputfile> <date?>
#
#  DESCRIPTION: aggregates feeds given a feeds text of links
#
# OPTIONS:
# AUTHOR:       Ayush
# VERSION:      2.0
# CREATED:      Apr 22, 2013 6:38:02 AM EST
# REVISION:     ---
#===============================================================================


use strict;
use warnings;


use DateTime;
use LWP::Simple;
use Time::Local;


#================Returns a 80 column line formatted string===========================
sub sizedColm {
	my $str    = $_[0];
	my @words = split( / /, $str );
	my $returnstr = "";

	#Size to 80 columns
	my $len = 0;
	foreach my $var (@words) {
		$len += ( length($var) + 1 );
		if ( $len > 80 ) {
			$returnstr = $returnstr . "\n";
			$len    = ( length($var) + 1 );
		}
		$returnstr = $returnstr . $var . " ";
	}
	return $returnstr;
}


#================Converts date to unixtime====================================
# eg. Mon, 15 Oct 2007 09:10:00 EST / 2007-04-12T07:07:07 / 23/04/2012
# timelocal($sec,$min,$hour,$mday,$mon,$year);

sub unixTime {
  my %months =
    qw( jan 0 feb 1 mar 2 apr 3 may 4 jun 5 jul 6 aug 7 sep 8 oct 9 nov 10 dec 11);

  print "Converting $_[0]...\n";
  # Use // instead of {}..too confusing
  if ( $_[0] =~ /^\s*.*,\s*(..)\s*(...)\s*(....)\s*(..):(..):(..)/ ) {

    print("$1: $2: $3: $4: $5: $6\n");

    my $t =
      timelocal( $6, $5, $4, $1, $months{ lc($2) }, ( int $3 ) - 1900 );

    print "Generated string is $t\n";
    print "Parsed string is " . localtime($t) . "\n";
    return $t;
  } else {
    if($_[0] =~ /^(....)-(..)-(..).(..):(..):(..)\s*/){

    print("$1: $2: $3: $4: $5: $6\n");

    my $t =
      timelocal( $6, $5, $4, $3, $2, ( int $1 ) - 1900 );

    print "Generated string is $t\n";
    print "Parsed string is " . localtime($t) . "\n";
    return $t;
    } else {
    	return -1;
    }
  }
  print "Non standard Date!";
}

#=======================Writes an html file give filename and the sorted array========
# filename, hashref of channels
sub printHTML {

	my $filename = shift;
	my $channels = shift;
	open my $fh, ">$filename" or die "Could not open: $!";

	select $fh;

print <<"HTML";
<table cellpadding=1 align="center">
    <tr>
HTML
        for my $title (keys %$channels){
            my @channelarray = @{$channels->{$title}};
print <<"HTML";
        <td bgcolor="#000000">
    <table cellpadding=5>
        <tr>
            <td bgcolor="#aaaaaa" align="center">
HTML
                    
                print qq|$title<br>\n|;
print <<"HTML";
            </td>
        </tr>
        <tr>
            <td bgcolor="#bbbbff" width=200>
                <font size="-1">
HTML
                    # print 
                    for my $itms (@channelarray){
                        # for my $key (keys %$itms){
                            print qq|<b></b><a href="$itms->{link}">$itms->{title}</a><br><br>\n|;
                            
                        # }
                    }
print <<"HTML";
                </font>
            </td>
        </tr>
    </table>
        </td>
HTML
    }
print <<"HTML";
    </tr>
</table>
HTML
close $fh;

}

#=================Writes a text file given filename and the sorted array============
#filename, array of items

sub printTXT {

	my $filename = shift;
	my $completeItems = shift;

	open my $fh, ">$filename" or die "Could not open: $!";
	select $fh;

	for my $itms (@$completeItems){
	      for my $val (keys %$itms){
	        if($val eq "pubDate"){
	          
	          print "$val: ".localtime($itms->{$val});
	        } else {
	          print "$val: $itms->{$val}\n" if ($itms->{$val} ne '');
	        }
	      }
	      print "-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";
	    }
	   

	close $fh;
}

#==============Writes an xml file given filename and the sorted array=============
#filename, array of items

sub printXML{

	my $filename = shift;
	my $completeItems = shift;
	# my @sorted =
	#   		sort { $b->{pubDate} <=> $a->{pubDate} } @completeItems;
	
	open my $fh, ">$filename" or die "Could not open: $!";

	select $fh;
	#write the css headers
	print "<?xml version=\"1.0\"?>\n <rss version=\"2.0\">
		<channel>
			<title>RMIT News</title>
			<link>http://rmit.edu.au/</link>
			<description>RSS feeds aggregation</description>
			<language>en-us</language>
			<pubDate>Mon, 22 Apr 2013 07:15:11 GMT</pubDate>
			<lastBuildDate>Mon, 22 Apr 2013 09:15:11 GMT</pubDate>
			<docs>http://www.engadget.com/rss.xml</docs>
			<generator>RMIT yallara-perl</perl>
			<managingEditor>editor\@example.com</managingEditor>
			<webMaster>webMaster\@example.com</webMaster>\n\n";

	 foreach my $itms (@$completeItems){
		print "<item>\n";
		print "<title>" . $itms->{title} . "</title>\n";
		print "<author>" . $itms->{author} . "</author>\n";
		print "<pubDate>" . localtime($itms->{pubDate}) . "</pubDate>\n";
		print "<description>" . $itms->{description} . "</description>\n";
		print "</item>\n";
		print "\n\n";
	}
	print "</channel>\n</rss>";
	close $fh;
}

#********************
#	MAIN PROGRAM	#
#********************

if ( scalar(@ARGV) < 2 ) {
	die "Usage:./aggregator.pl <feeds text> <outputfile> <date?>\n";
}

#open text file and read the urls (one per line)
my $urlfile = shift;
open my $rssfh, "<", $urlfile or die "Could not open: $!";
my $data = "";
while(my $url = <$rssfh>){
  print $url;
  $data .= get $url or die "DID you reach here? $!";
}
close $rssfh;

#read all the channels one by one accumulating all the items

my @tags = ("title", "link", "description", "pubDate", "author"); 
my @itemsrefd = ();
my $channels = {};
my @completeItems = ();
my $href ;
while ($data =~ m{<channel>(.*?)</channel>}gs) {
    my $chunk = $1;
    my $d = $chunk;
    $d =~ m{<title>(.*?)</title>}igs;
    my $channeltitle = $1;
    my $channelref = {};
    while ($chunk =~ m{<item>(.*?)</item>}gs)
    {
        my $temp = $1;
        $href = {};
        my $stuff;
        foreach my $itemElement (@tags){
            $temp =~ m{<$itemElement>(.*?)</$itemElement>}igs;
            $stuff          = $1;
            #Remove the CDATA, if any
            my $checkstuff = $stuff;
            if($checkstuff =~ m{<\!\[CDATA\[(.*?)\]\]>}igs){
              my $flag = $1;
              $stuff = $flag; 
            }
         # Removing the HTML tags
         $stuff =~ s{<(?:[^>'"]*|(['"]).*?\1)*>}{}gs;
         # Removing the non-ascii characters
         $stuff =~ s{[^[:ascii:]]+}{}g; 
         $href->{$itemElement} = $stuff;
         # If nothing found say Unknown, unless its a date
         if($href->{$itemElement} eq ''){
            if($itemElement eq "pubDate"){
                my $dt = DateTime->now;
                my $date = $dt->ymd;
                my $time = $dt->hms;
                $href->{$itemElement} = "$date $time";   
            } else {
                $href->{$itemElement} = "Unknown";
            }
         }
         if($itemElement eq "pubDate"){
            $href->{$itemElement} = (unixTime $href->{$itemElement});
          }
        }
        push @itemsrefd,{%$href};
    }
    # $channelref->{$channeltitle} = $itemsrefd;
    # push channels, $channelref;
    $channels->{$channeltitle} = [@itemsrefd];
    @completeItems = (@completeItems , @itemsrefd);
}

#sort array based on date
my @sorted =
	  sort { $b->{pubDate} <=> $a->{pubDate} } @completeItems;

my $index = @sorted;
# check for the remaining arguments
my $base = shift;

if( defined $ARGV[2]) {
	my $fromDate = unixTime( $ARGV[2]);
	if( $fromDate == -1){
		print "Invalid date supplied. Printing all the items..";
	}

	my $count = 0;

	foreach my $items (@sorted) {
		if ( $items->{pubDate} lt $fromDate ){
			$index = $count + 1;
			last;
		}
		$count = $count + 1;
	}
}

my @final = splice( @sorted, 0, $index );

#check the file type to write to 
my @filecomponents = split(/\./, $base );
if ( $filecomponents[-1] eq "xml") {
	printXML( $base, \@final );
} elsif ( $filecomponents[-1] eq "txt" ) {
	printTXT( $base, \@final );
} elsif ( $filecomponents[-1] eq "html" ) {
	printXML( $base, \@final );
} else {
	print "Unknown File format!";
}

print( -s $base);
print " bytes written to disk\n";