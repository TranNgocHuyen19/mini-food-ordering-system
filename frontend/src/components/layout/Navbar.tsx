import { Link, useNavigate } from 'react-router-dom';
import { ShoppingCart, LogOut, User, Menu as MenuIcon } from 'lucide-react';
import { useAuth } from '@/context/AuthContext';
import { useCart } from '@/context/CartContext';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';

export const Navbar = () => {
  const { user, logout } = useAuth();
  const { totalItems } = useCart();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="container mx-auto flex h-16 items-center px-4">
        {/* Left Section: Logo */}
        <div className="flex-1 flex items-center">
          <Link to="/" className="flex items-center gap-2 hover:opacity-90 transition-opacity">
            <div className="bg-primary text-primary-foreground p-2 rounded-lg shadow-lg shadow-primary/20">
              <MenuIcon size={20} />
            </div>
            <span className="text-xl font-black tracking-tighter">MiniFood</span>
          </Link>
        </div>

        {/* Center Section: Navigation Links */}
        <div className="hidden md:flex items-center gap-2">
          <Link to="/">
            <Button variant="ghost" className="rounded-full px-6 font-bold tracking-wide">Menu</Button>
          </Link>
          {user && (
            <Link to="/orders">
              <Button variant="ghost" className="rounded-full px-6 font-bold tracking-wide">My Orders</Button>
            </Link>
          )}
        </div>

        {/* Right Section: Auth & Cart */}
        <div className="flex-1 flex items-center justify-end gap-2">
          {!user ? (
            <div className="flex items-center gap-2">
              <Link to="/login">
                <Button variant="ghost" className="font-bold">Login</Button>
              </Link>
              <Link to="/register">
                <Button className="rounded-full px-6 font-bold shadow-lg shadow-primary/20">Register</Button>
              </Link>
            </div>
          ) : (
            <div className="flex items-center gap-3 border-l pl-3 ml-1">
              <Link to="/profile" className="flex items-center gap-2 p-1.5 px-3 bg-muted/50 rounded-full hover:bg-muted transition-colors">
                <User size={16} className="text-primary" />
                <span className="text-xs font-black uppercase tracking-widest">{user.username}</span>
              </Link>
              <Button variant="ghost" size="icon" className="rounded-full h-9 w-9 text-muted-foreground hover:text-destructive transition-colors" onClick={handleLogout} title="Logout">
                <LogOut size={16} />
              </Button>
            </div>
          )}

          <Link to="/cart">
            <Button variant="outline" size="icon" className="relative h-10 w-10 ml-2 rounded-full border-2 border-border/40 hover:border-primary/40 hover:bg-primary/5 group transition-all">
              <ShoppingCart size={20} className="group-hover:scale-110 transition-transform" />
              {totalItems > 0 && (
                <Badge
                  className="absolute -top-1.5 -right-1.5 h-5 min-w-5 flex items-center justify-center rounded-full p-0 text-[10px] font-black border-2 border-background"
                  variant="destructive"
                >
                  {totalItems}
                </Badge>
              )}
            </Button>
          </Link>
        </div>
      </div>
    </nav>
  );
};
