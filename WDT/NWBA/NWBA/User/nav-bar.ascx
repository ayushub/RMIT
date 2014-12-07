<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="nav-bar.ascx.cs" Inherits="NWBA.User.nav_bar" %>
<asp:Menu  ID="Menu1" runat="server" BackColor="#E3EAEB" DynamicHorizontalOffset="2" Font-Names="Verdana" Font-Size="0.8em" ForeColor="#666666" StaticSubMenuIndent="10px" Width="700px" Orientation="Horizontal" style="text-align: center">
        <DynamicHoverStyle BackColor="#666666" ForeColor="White" />
        <DynamicMenuItemStyle HorizontalPadding="5px" ItemSpacing="20px" VerticalPadding="2px" />
        <DynamicMenuStyle BackColor="#E3EAEB" />
        <DynamicSelectedStyle BackColor="#1C5E55" />
        <Items>
            <asp:MenuItem Text="ATM " ToolTip="Deposit/Withdraw" Value="ATM "></asp:MenuItem>
            <asp:MenuItem Text="My Statement" ToolTip="View transaction history" Value="My Statement"></asp:MenuItem>
            <asp:MenuItem Text="My Profile" ToolTip="Account management" Value="My Profile"></asp:MenuItem>
        </Items>
        <StaticHoverStyle BackColor="#666666" ForeColor="White" />
        <StaticMenuItemStyle HorizontalPadding="70px" ItemSpacing="30px" VerticalPadding="2px" />
        <StaticSelectedStyle BackColor="#1C5E55" />
    </asp:Menu>