using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NWBA;

namespace NWBA.App_Code
{
    public class Util
    {
        bankDB_WDT1234Entities context = new bankDB_WDT1234Entities();

        public Decimal getBalance(int accNumber)
        {
           var sourceAccTransctions = context.Transactions.Where(t => t.SourceAccount == accNumber);
           var destAccTransctions = context.Transactions.Where(t => t.TransactionDestination.DestAccount == accNumber);
            Decimal balance = 0;

            foreach (var t in sourceAccTransctions)
            {
                if(t.TransactionType =="D")
                    balance += Convert.ToDecimal(t.Amount);
                else
                    balance -= Convert.ToDecimal(t.Amount);
            }

            foreach (var t in destAccTransctions)
            {
                balance += Convert.ToDecimal(t.Amount);
              
            }
            //     int balance = dc.Transactions.Where(t=>t.a)
            return balance;
        }

        public bool hasFreeTransaction(int CustomerID)
        {
            int TranstCount = context.Transactions.Where(t => t.Account.CustomerID == CustomerID).Count();
             System.Diagnostics.Trace.WriteLine("gggg " + TranstCount);
            if (TranstCount < 4)
            {
                return true;
            }

            return false;
        }

        public Char getAccType(int accNumber)
        {
            return 'C';
        }
    }
}