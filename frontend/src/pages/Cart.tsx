import { Link, useNavigate } from 'react-router-dom';
import { motion, AnimatePresence } from 'framer-motion';
import { Trash2, Plus, Minus, ShoppingBag, ArrowRight } from 'lucide-react';
import { useCart } from '../context/CartContext';
import { Button } from '@/components/ui/button';
import { Card, CardHeader, CardTitle, CardContent, CardFooter } from '@/components/ui/card';
import { Separator } from '@/components/ui/separator';
import { ScrollArea } from '@/components/ui/scroll-area';
import { useAuth } from '../context/AuthContext';
import { toast } from 'sonner';

export const Cart = () => {
  const { cart, removeFromCart, updateQuantity, clearCart, totalPrice, totalItems } = useCart();
  const { user } = useAuth();
  const navigate = useNavigate();

  const handleCheckout = () => {
    if (!user) {
      toast.error('Please login to continue checkout');
      navigate('/login');
    } else {
      navigate('/checkout');
    }
  };

  if (cart.length === 0) {
    return (
      <div className="flex h-[75vh] flex-col items-center justify-center p-8 text-center bg-muted/20 rounded-3xl backdrop-blur-sm mt-8 border-2 border-dashed border-muted/50">
        <motion.div
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
          className="bg-primary/10 p-10 rounded-full mb-8 text-primary shadow-inner"
        >
          <ShoppingBag size={80} strokeWidth={1.5} />
        </motion.div>
        <h2 className="text-4xl font-black mb-4 tracking-tight">Your cart is empty</h2>
        <p className="text-muted-foreground text-lg mb-10 max-w-md mx-auto">
          It looks like you haven&apos;t added any delicious items to your cart yet. 
        </p>
        <Link to="/">
          <Button variant="default" size="lg" className="rounded-full h-14 px-10 font-bold text-lg shadow-xl shadow-primary/20 hover:scale-105 active:scale-95 transition-all">
            Browse Menu
          </Button>
        </Link>
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-6xl pb-24 px-4 w-full grid grid-cols-1 lg:grid-cols-3 gap-12 pt-8">
      <div className="lg:col-span-2 space-y-8">
        <div className="flex items-center justify-between mb-2">
          <h1 className="text-3xl font-black tracking-tight">Your Cart <span className="text-muted-foreground font-medium text-xl ml-2">({totalItems} items)</span></h1>
          <Button 
            variant="ghost" 
            className="text-destructive font-semibold hover:bg-destructive/10"
            onClick={clearCart}
          >
            Clear Cart
          </Button>
        </div>

        <ScrollArea className="h-full rounded-2xl border-none">
          <AnimatePresence mode="popLayout">
            {cart.map((item) => (
              <motion.div
                key={item.food.id}
                layout
                initial={{ opacity: 0, x: -20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, scale: 0.95 }}
                className="mb-6 p-6 rounded-2xl bg-card/60 backdrop-blur-sm border-none shadow-sm hover:shadow-lg transition-all duration-300 group"
              >
                <div className="flex items-center gap-6">
                  <div className="h-24 w-24 overflow-hidden rounded-xl shadow-lg shrink-0">
                    <img 
                      src={item.food.imageUrl} 
                      alt={item.food.name} 
                      className="h-full w-full object-cover transition-transform duration-500 group-hover:scale-110" 
                    />
                  </div>
                  <div className="flex-1 min-w-0 flex flex-col justify-between h-full">
                    <div className="flex justify-between items-start">
                      <div>
                        <h3 className="text-lg font-bold truncate leading-tight mb-1">{item.food.name}</h3>
                        <p className="text-sm text-muted-foreground font-medium mb-2">{item.food.category}</p>
                      </div>
                      <span className="text-xl font-black text-primary">${(item.food.price * item.quantity).toFixed(2)}</span>
                    </div>
                    <div className="flex items-center justify-between mt-4">
                      <div className="flex items-center gap-1 bg-muted/60 p-1 rounded-xl border border-border/20 shadow-inner">
                        <Button
                          variant="ghost"
                          size="icon"
                          className="h-8 w-8 rounded-lg hover:bg-background shadow-sm hover:text-primary transition-all"
                          onClick={() => updateQuantity(item.food.id, item.quantity - 1)}
                        >
                          <Minus size={14} />
                        </Button>
                        <span className="w-10 text-center font-bold text-sm tracking-tight">{item.quantity}</span>
                        <Button
                          variant="ghost"
                          size="icon"
                          className="h-8 w-8 rounded-lg hover:bg-background shadow-sm hover:text-primary transition-all"
                          onClick={() => updateQuantity(item.food.id, item.quantity + 1)}
                        >
                          <Plus size={14} />
                        </Button>
                      </div>
                      <Button
                        variant="ghost"
                        size="icon"
                        className="text-muted-foreground hover:text-destructive hover:bg-destructive/10 transition-colors h-10 w-10"
                        onClick={() => removeFromCart(item.food.id)}
                      >
                        <Trash2 size={18} />
                      </Button>
                    </div>
                  </div>
                </div>
              </motion.div>
            ))}
          </AnimatePresence>
        </ScrollArea>
      </div>

      <div className="lg:col-span-1">
        <motion.div 
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="sticky top-32"
        >
          <Card className="border-none bg-card/40 backdrop-blur-xl shadow-2xl p-4 overflow-hidden">
            <div className="absolute top-0 right-0 p-12 opacity-5 pointer-events-none transform translate-x-1/2 -translate-y-1/2">
              <ShoppingBag size={200} className="text-primary" />
            </div>
            <CardHeader className="pb-4">
              <CardTitle className="text-2xl font-black tracking-tight">Order Summary</CardTitle>
            </CardHeader>
            <CardContent className="space-y-6 pt-0">
              <div className="space-y-4">
                <div className="flex justify-between font-medium text-muted-foreground">
                  <span>Subtotal</span>
                  <span className="text-foreground">${totalPrice.toFixed(2)}</span>
                </div>
                <div className="flex justify-between font-medium text-muted-foreground">
                  <span>Delivery Fee</span>
                  <span className="text-primary font-bold">Free</span>
                </div>
                <Separator className="bg-border/30" />
                <div className="flex justify-between text-2xl font-black tracking-tight">
                  <span>Total</span>
                  <span className="text-primary">${totalPrice.toFixed(2)}</span>
                </div>
              </div>
            </CardContent>
            <CardFooter className="pt-4 flex flex-col gap-4">
              <Button 
                onClick={handleCheckout} 
                className="w-full h-14 rounded-2xl text-lg font-bold shadow-xl shadow-primary/20 transition-all hover:translate-y-[-2px] active:translate-y-[1px]"
              >
                Checkout Now
                <ArrowRight className="ml-2" size={20} />
              </Button>
              <Link to="/" className="w-full">
                <Button variant="ghost" className="w-full h-12 rounded-xl text-muted-foreground hover:text-foreground font-semibold">
                  Continue Shopping
                </Button>
              </Link>
            </CardFooter>
          </Card>
        </motion.div>
      </div>
    </div>
  );
};
