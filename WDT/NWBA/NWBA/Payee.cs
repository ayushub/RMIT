//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace NWBA
{
    using System;
    using System.Collections.Generic;
    
    public partial class Payee
    {
        public Payee()
        {
            this.BillPays = new HashSet<BillPay>();
        }
    
        public int PayeeID { get; set; }
        public string PayeeName { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public string PostCode { get; set; }
        public string Phone { get; set; }
    
        public virtual ICollection<BillPay> BillPays { get; set; }
    }
}