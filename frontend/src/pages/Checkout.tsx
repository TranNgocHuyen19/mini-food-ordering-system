import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { CreditCard, Truck, MapPin, CheckCircle2, ChevronRight, ShoppingBag } from 'lucide-react';
import { useCart } from '../context/CartContext';
import { orderService } from '../services';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardHeader, CardTitle, CardContent, CardFooter, CardDescription } from '@/components/ui/card';
import { Separator } from '@/components/ui/separator';
import { useAuth } from '../context/AuthContext';
import { toast } from 'sonner';

export const Checkout = () => {
  const { cart, totalPrice, clearCart } = useCart();
  const { user } = useAuth();
  const [address, setAddress] = useState('');
  const [phone, setPhone] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [isOrdered, setIsOrdered] = useState(false);
  const navigate = useNavigate();

  const handlePlaceOrder = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!address || !phone) {
      toast.error('Please fill in your address and phone number');
      return;
    }

    setIsLoading(true);
    try {
      await orderService.createOrder({
        userId: user?.id,
        items: cart,
        totalAmount: totalPrice,
        address,
        phone,
      });
      setIsOrdered(true);
      clearCart();
      toast.success('Order placed successfully!');
    } catch (error: any) {
      toast.error('Failed to place order: ' + (error.message || 'Please try again later.'));
    } finally {
      setIsLoading(false);
    }
  };

  if (isOrdered) {
    return (
      <div className="flex flex-col items-center justify-center p-8 text-center bg-green-50/50 backdrop-blur-xl rounded-3xl mt-12 py-24 shadow-2xl border border-green-500/20">
        <motion.div
          initial={{ scale: 0, rotate: -45 }}
          animate={{ scale: 1, rotate: 0 }}
          transition={{ type: 'spring', stiffness: 260, damping: 20 }}
          className="bg-green-500 h-24 w-24 rounded-full flex items-center justify-center shadow-2xl shadow-green-500/50 mb-8"
        >
          <CheckCircle2 color="white" size={48} strokeWidth={3} />
        </motion.div>
        <h2 className="text-5xl font-black text-green-950 mb-4 tracking-tighter">Order Placed!</h2>
        <p className="text-green-800 text-xl font-medium max-w-sm mb-12 leading-relaxed">
          Your delicious meal will be at your door in approximately <span className="font-black underline decoration-green-300">30-45 minutes</span>.
        </p>
        <div className="flex gap-4">
          <Button variant="default" size="lg" className="bg-green-700 hover:bg-green-800 rounded-full h-14 px-10 text-lg font-bold shadow-xl shadow-green-900/20 active:scale-95 transition-all" onClick={() => navigate('/orders')}>
            View My Orders
          </Button>
          <Button variant="outline" size="lg" className="border-green-200 hover:bg-green-100 rounded-full h-14 px-10 text-lg font-bold shadow-md hover:shadow-lg transition-all" onClick={() => navigate('/')}>
            Back to Menu
          </Button>
        </div>
      </div>
    );
  }

  if (cart.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center pt-24 space-y-6">
        <div className="bg-muted p-8 rounded-full text-muted-foreground opacity-50">
          <ShoppingBag size={64} />
        </div>
        <h2 className="text-4xl font-black text-center tracking-tight">Your Session Expired</h2>
        <p className="text-muted-foreground text-center text-lg">Go back to the menu to pick some items.</p>
        <Button onClick={() => navigate('/')} className="rounded-full h-12 px-8 font-bold text-lg shadow-lg">Pick Some Food</Button>
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-6xl pb-24 px-4 w-full pt-8 animate-in fade-in slide-in-from-bottom-4 duration-700">
      <div className="flex items-baseline gap-2 mb-10 overflow-hidden">
        <h1 className="text-4xl font-black tracking-tight whitespace-nowrap">Checkout</h1>
        <div className="h-[2px] bg-border/30 w-full animate-grow-x" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-12">
        <div className="lg:col-span-2 space-y-8">
          <form onSubmit={handlePlaceOrder} className="space-y-8">
            <Card className="border-none bg-card shadow-2xl rounded-3xl overflow-hidden p-4">
              <CardHeader className="pt-6 pb-4">
                <CardTitle className="text-xl font-bold flex items-center gap-3">
                  <div className="p-2 bg-primary/10 text-primary rounded-xl shrink-0"><MapPin size={24} /></div>
                  Delivery Information
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-6">
                <div className="grid gap-6 md:grid-cols-2">
                  <div className="space-y-3">
                    <Label htmlFor="address" className="font-bold text-sm tracking-wide">Full Delivery Address</Label>
                    <Input
                      id="address"
                      placeholder="Street name, house number, area"
                      required
                      value={address}
                      onChange={(e) => setAddress(e.target.value)}
                      className="bg-muted/30 border-none font-medium h-12 focus:ring-2 focus:ring-primary/20 transition-all text-lg pl-4"
                    />
                  </div>
                  <div className="space-y-3">
                    <Label htmlFor="phone" className="font-bold text-sm tracking-wide">Contact Phone Number</Label>
                    <Input
                      id="phone"
                      type="tel"
                      placeholder="+1 (555) 000-0000"
                      required
                      value={phone}
                      onChange={(e) => setPhone(e.target.value)}
                      className="bg-muted/30 border-none font-medium h-12 focus:ring-2 focus:ring-primary/20 transition-all text-lg pl-4"
                    />
                  </div>
                </div>
              </CardContent>
            </Card>

            <Card className="border-none bg-card shadow-2xl rounded-3xl overflow-hidden p-4">
              <CardHeader className="pt-6 pb-4">
                <CardTitle className="text-xl font-bold flex items-center gap-3">
                  <div className="p-2 bg-primary/10 text-primary rounded-xl shrink-0"><CreditCard size={24} /></div>
                  Payment Method
                </CardTitle>
                <CardDescription className="font-medium text-muted-foreground ml-12">Only Cash on Delivery is available for now.</CardDescription>
              </CardHeader>
              <CardContent className="pt-4 px-6 pb-8">
                <div className="p-6 rounded-2xl bg-primary/5 border-2 border-primary/20 flex items-center justify-between group cursor-default transition-all duration-300 hover:border-primary/40">
                  <div className="flex items-center gap-4">
                    <Truck className="text-primary" size={24} />
                    <div>
                      <h4 className="font-black text-lg text-primary tracking-tight">Cash on Delivery</h4>
                      <p className="text-primary/70 text-sm font-semibold">Pay when you receive your food</p>
                    </div>
                  </div>
                  <div className="bg-primary text-white h-8 w-8 rounded-full flex items-center justify-center shadow-lg shadow-primary/30 group-hover:scale-110 transition-transform"><CheckCircle2 size={18} strokeWidth={3} /></div>
                </div>
              </CardContent>
            </Card>
          </form>
        </div>

        <div className="lg:col-span-1">
          <Card className="sticky top-28 border-none bg-primary text-primary-foreground shadow-2xl rounded-3xl overflow-hidden p-2 transform rotate-1 transition-transform hover:rotate-0 duration-500">
            <div className="bg-background text-foreground h-full w-full rounded-2xl p-6 relative overflow-hidden">
               <div className="absolute top-0 right-0 p-12 opacity-5 pointer-events-none transform translate-x-1/2 -translate-y-1/2">
                <ChevronRight size={160} />
              </div>
              <CardHeader className="pt-4 pb-4 px-0">
                <CardTitle className="text-2xl font-black tracking-tighter">Your Order</CardTitle>
              </CardHeader>
              <CardContent className="pt-0 px-0 space-y-6">
                <div className="max-h-60 overflow-y-auto space-y-4 pr-2 scrollbar-hide py-2">
                  {cart.map((item) => (
                    <div key={item.food.id} className="flex justify-between items-center group">
                      <div className="flex gap-3">
                        <span className="font-black text-primary bg-primary/10 h-6 w-6 rounded flex items-center justify-center text-xs">{item.quantity}×</span>
                        <span className="font-bold text-sm tracking-tight truncate max-w-[120px]">{item.food.name}</span>
                      </div>
                      <span className="font-black text-sm tracking-widest">${(item.food.price * item.quantity).toFixed(2)}</span>
                    </div>
                  ))}
                </div>
                <Separator className="bg-border/20" />
                <div className="space-y-4">
                  <div className="flex justify-between text-muted-foreground font-bold text-sm">
                    <span>Subtotal</span>
                    <span>${totalPrice.toFixed(2)}</span>
                  </div>
                  <div className="flex justify-between text-muted-foreground font-bold text-sm">
                    <span>Delivery</span>
                    <span className="text-primary uppercase tracking-widest font-black">Free</span>
                  </div>
                  <Separator className="bg-border/20" />
                  <div className="flex justify-between text-2xl font-black tracking-tighter pt-2">
                    <span>Total</span>
                    <span className="text-primary">${totalPrice.toFixed(2)}</span>
                  </div>
                </div>
              </CardContent>
              <CardFooter className="pt-8 px-0 flex flex-col gap-4">
                <Button 
                  disabled={isLoading} 
                  onClick={handlePlaceOrder} 
                  className="w-full h-16 rounded-2xl text-xl font-black shadow-2xl shadow-primary/40 hover:shadow-primary/60 active:scale-95 transition-all"
                >
                  {isLoading ? 'Processing...' : 'Confirm Order'}
                </Button>
                <p className="text-[10px] text-center text-muted-foreground font-black uppercase tracking-widest leading-loose">
                  By confirming, you agree to our <br /> Terms of Delicious Service
                </p>
              </CardFooter>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
};
