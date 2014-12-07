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

use LWP::Simple;

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

#***************
# MAIN PROGRAM #
#***************

my $url = shift;
my $data = get $url or die "Couldn't fetch the URL: $!";
#url validation can be or die "$!";
my @tags = ("title", "link", "description", "pubDate", "author"); 
my @itemsrefd;
my $href ;
while ($data =~ m{<item>(.*?)</item>}gs)
{
	my $temp = $1;
    $href = {};
    my $stuff;
    foreach my $itemElement (@tags){
		$temp =~ m{<$itemElement>(.*?)</$itemElement>}igs;	    		
    	$stuff 			= $1;
       	# print $stuff;
    	my $checkstuff = $stuff;
       	if($checkstuff =~ m{<!\[CDATA\[(.*?)\]\]>}igs){
    		my $flag = $1;
    		$stuff = $flag;	
    	}
    	if($itemElement eq "description"){
       		$stuff =~ m{<p>(.*?)</p>}igs;
    		$stuff = $1;
    	}
      	$href->{$itemElement} = $stuff;	
      	if($href->{$itemElement} eq ''){
      		$href->{$itemElement} = "Unknown";
      	}
    }
    push @itemsrefd,$href;
}
for my $h ( @itemsrefd){
	print "{";
	for my $val (keys %$h){
			print sizedColm "$val: $h->{$val}\n";	
	}
	print "}\n";
	next:
}