function daysDifference(d1,d2) {
  var millis_per_day = 24 * 60 * 60 * 1000
  var utcDate1 = Date.UTC(d1.getFullYear(), d1.getMonth(), d1.getDate());
  var utcDate2 = Date.UTC(d2.getFullYear(), d2.getMonth(), d2.getDate());
  var intDaysDiff = Math.floor((utcDate1-utcDate2) / millis_per_day);
  return intDaysDiff
}

var today = new Date();
var mar1 = new Date("3/1/2016");
daysDifference(today,mar1)
// -17
