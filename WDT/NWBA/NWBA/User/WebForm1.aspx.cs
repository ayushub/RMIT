using NWBA.App_Code;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace NWBA.User
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {  
            BusinessTransactions obj = new BusinessTransactions();
          //  Label1.Text = obj.doWithDrawal(20,400);
            Util u = new Util();
            Label1.Text = u.getBalance(4).ToString() + "    " + obj.doWithDrawal(4,170) + " statement:" + obj.getStatement(4) + " statement:" + obj.getStatement(4);
        }
    }
}