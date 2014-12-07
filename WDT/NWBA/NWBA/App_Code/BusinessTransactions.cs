using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace NWBA.App_Code
{
    public class BusinessTransactions
    {
        bankDB_WDT1234Entities context = new bankDB_WDT1234Entities();
        Util u = new Util();

        public String doWithDrawal(int accountNumber, Decimal amount)
        {
            //amount will be negative as we are deducting
            if (doATMTransction("W", accountNumber, -amount) == "")
            {
                return "Done";
            }

            return "Error in transaction";
        }

        public String doDeposit(int accountNumber, decimal amount)
        {
            if (doATMTransction("D", accountNumber, amount) == "")
            {
                return "Done";
            }

            return "Error in transaction";
        }

        public String getStatement(int accountNumber)
        {
            //return doATMTransction(accountNumber);
            if (doATMTransction("V", accountNumber, Convert.ToDecimal(u.hasFreeTransaction(1) ? 0 : 0.20)) == "")
            {
                return "Done"; 
            }

            return "Error in transaction";
        }



        private String doATMTransction(String transactionType, int accNumber, decimal amount, String comment = "")
        {
          
            //String ErrorString = "";
            //parsing required as it was not oprerating by assuming it double
            Decimal total = u.getBalance(accNumber) + amount - Convert.ToDecimal(u.hasFreeTransaction(1) ? 0 : 0.20);
            Char AccType = u.getAccType(accNumber);

            System.Diagnostics.Trace.WriteLine("fdsfdsfd "+total);

            if (AccType == 'C')
            {
                if (total > 200)
                {
                    return "" ;
                }
                else
                    return "not enough balance to do tranction";
            }

            if (AccType == 'S')
            {
                if (total > 0)
                {
                    return "";
                }
                else
                    return "not enough balance to do transaction";
            }


            if (AddTranscInDB(transactionType, accNumber, total, comment))
            {
                return "";
            }
            else
            return "Transaction falied !!! \n couldn't add transaction to Database";
        }

        private bool AddTranscInDB(String transactionType, int accountNumber, decimal amount, String comment = "")
        {
            try
            {
                context.Transactions.Add(new Transaction()
                {
                    TransactionType = transactionType,
                    SourceAccount = accountNumber,
                    Amount = amount,
                    Comment = comment,
                    ModifyDate = DateTime.Now
                });
                context.SaveChanges();
                return true;
            }
            catch (Exception e) //yet to find specific exception type
            {
                return false;
            }
        }
    }
}