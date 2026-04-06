import { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Clock, CheckCircle2, Truck, Package, ChevronRight, MapPin, Phone } from 'lucide-react';
import { orderService } from '../services';
import type { Order } from '../types';
import { Card, CardFooter } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Skeleton } from '@/components/ui/skeleton';
import { Button } from '@/components/ui/button';
import { toast } from 'sonner';

export const Orders = () => {
  const [orders, setOrders] = useState<Order[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const data = await orderService.getUserOrders();
        setOrders(data);
      } catch (error: any) {
        toast.error('Failed to load orders: ' + (error.message || 'Please try again later.'));
        // Placeholder data for demo
        setOrders([
          {
            id: 'ORD-001',
            userId: '1',
            items: [
              { food: { id: '1', name: 'Classic Burger', description: '', price: 12.99, imageUrl: '', category: 'Burger' }, quantity: 2 },
              { food: { id: '3', name: 'Caesar Salad', description: '', price: 9.99, imageUrl: '', category: 'Salad' }, quantity: 1 }
            ],
            totalAmount: 35.97,
            status: 'DELIVERING',
            address: 'Main Street 123',
            phone: '+1 (555) 000-0000',
            createdAt: new Date().toISOString()
          },
          {
            id: 'ORD-002',
            userId: '1',
            items: [
              { food: { id: '2', name: 'Pepperoni Pizza', description: '', price: 15.49, imageUrl: '', category: 'Pizza' }, quantity: 1 }
            ],
            totalAmount: 15.49,
            status: 'COMPLETED',
            address: 'Main Street 123',
            phone: '+1 (555) 000-0000',
            createdAt: new Date(Date.now() - 86400000).toISOString()
          }
        ]);
      } finally {
        setLoading(false);
      }
    };
    fetchOrders();
  }, []);

  const getStatusIcon = (status: Order['status']) => {
    switch (status) {
      case 'PENDING': return <Clock size={16} />;
      case 'PREPARING': return <Package size={16} />;
      case 'DELIVERING': return <Truck size={16} />;
      case 'COMPLETED': return <CheckCircle2 size={16} />;
      default: return <Clock size={16} />;
    }
  };

  const getStatusColor = (status: Order['status']) => {
    switch (status) {
      case 'PENDING': return 'bg-yellow-100 text-yellow-700 border-yellow-200';
      case 'PREPARING': return 'bg-blue-100 text-blue-700 border-blue-200';
      case 'DELIVERING': return 'bg-purple-100 text-purple-700 border-purple-200';
      case 'COMPLETED': return 'bg-green-100 text-green-700 border-green-200';
      default: return 'bg-gray-100 text-gray-700 border-gray-200';
    }
  };

  if (loading) {
    return (
      <div className="max-w-4xl mx-auto space-y-6 pt-10">
        <Skeleton className="h-12 w-64 rounded-xl" />
        {Array.from({ length: 3 }).map((_, i) => (
          <Skeleton key={i} className="h-48 w-full rounded-2xl" />
        ))}
      </div>
    );
  }

  return (
    <div className="max-w-5xl mx-auto pb-24 px-4 w-full pt-8">
      <header className="mb-12">
        <h1 className="text-4xl font-black tracking-tight mb-2">Order History</h1>
        <p className="text-lg text-muted-foreground font-medium">Keep track of all your delicious adventures.</p>
      </header>

      {orders.length === 0 ? (
        <div className="text-center py-24 bg-muted/20 rounded-3xl border-2 border-dashed border-muted/50">
          <div className="bg-muted p-8 rounded-full w-fit mx-auto mb-6 text-muted-foreground opacity-50">
            <Package size={48} />
          </div>
          <h2 className="text-2xl font-bold mb-2">No orders found</h2>
          <p className="text-muted-foreground mb-8">You haven&apos;t placed any orders yet.</p>
          <Button variant="default" size="lg" className="rounded-full px-8 shadow-xl" onClick={() => window.location.href = '/'}>
            Order Your First Meal
          </Button>
        </div>
      ) : (
        <div className="space-y-8">
          <AnimatePresence mode="popLayout">
            {orders.map((order, index) => (
              <motion.div
                key={order.id}
                initial={{ opacity: 0, x: -20 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.4, delay: index * 0.1 }}
                className="group"
              >
                <Card className="border-none bg-card/60 backdrop-blur-sm shadow-xl rounded-3xl overflow-hidden hover:shadow-2xl hover:bg-card transition-all duration-500">
                  <div className="p-8">
                    <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4 mb-8">
                      <div className="space-y-1">
                        <div className="flex items-center gap-3">
                          <h3 className="text-xl font-black tracking-tight">{order.id}</h3>
                          <Badge className={`rounded-full px-4 py-1 flex items-center gap-2 border shadow-sm ${getStatusColor(order.status)}`}>
                            {getStatusIcon(order.status)}
                            <span className="font-bold tracking-wide text-[11px] uppercase">{order.status}</span>
                          </Badge>
                        </div>
                        <p className="text-sm text-muted-foreground font-semibold flex items-center gap-1.5 opacity-70">
                          <Clock size={14} strokeWidth={2.5} />
                          {new Date(order.createdAt).toLocaleDateString()} at {new Date(order.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                        </p>
                      </div>
                      <div className="text-right">
                        <span className="block text-xs font-black text-muted-foreground uppercase tracking-[0.2em] mb-1">Total Amount</span>
                        <span className="text-3xl font-black text-primary tracking-tighter">${order.totalAmount.toFixed(2)}</span>
                      </div>
                    </div>

                    <div className="grid md:grid-cols-2 gap-8 mb-4">
                      <div className="space-y-4">
                         <h4 className="text-xs font-black text-muted-foreground uppercase tracking-[0.2em] border-b border-border/30 pb-2">Items Ordered</h4>
                         <div className="space-y-3">
                          {order.items.map((item, i) => (
                            <div key={i} className="flex justify-between items-center bg-muted/30 p-3 rounded-xl hover:bg-primary/5 transition-colors group/item">
                              <div className="flex items-center gap-3">
                                <span className="font-black text-primary bg-primary/10 h-6 w-8 rounded flex items-center justify-center text-xs">{item.quantity}×</span>
                                <span className="font-bold text-sm tracking-tight">{item.food.name}</span>
                              </div>
                              <span className="font-black text-xs text-muted-foreground opacity-0 group-hover/item:opacity-100 transition-opacity">${(item.food.price * item.quantity).toFixed(2)}</span>
                            </div>
                          ))}
                        </div>
                      </div>

                      <div className="space-y-4">
                         <h4 className="text-xs font-black text-muted-foreground uppercase tracking-[0.2em] border-b border-border/30 pb-2">Delivery Details</h4>
                         <div className="space-y-3">
                            <div className="flex items-start gap-3 p-3 rounded-xl">
                              <div className="p-2 bg-primary/10 text-primary rounded-lg shrink-0"><MapPin size={16} /></div>
                              <span className="text-sm font-bold text-muted-foreground leading-snug pt-1 overflow-hidden text-ellipsis line-clamp-2">{order.address}</span>
                            </div>
                            <div className="flex items-center gap-3 p-3 rounded-xl mt-[-8px]">
                              <div className="p-2 bg-primary/10 text-primary rounded-lg shrink-0"><Phone size={16} /></div>
                              <span className="text-sm font-black text-muted-foreground">{order.phone}</span>
                            </div>
                         </div>
                      </div>
                    </div>
                  </div>
                  <CardFooter className="bg-muted/30 px-8 py-4 flex justify-between items-center group-hover:bg-primary/5 transition-all">
                    <button className="text-xs font-bold text-primary hover:underline flex items-center gap-1.5 transition-all">
                      View full invoice <ChevronRight size={14} />
                    </button>
                    <button className="bg-background text-foreground hover:bg-foreground hover:text-background border border-border px-6 py-2 rounded-xl text-xs font-black tracking-widest uppercase shadow-sm transition-all active:scale-95">
                      Order Again
                    </button>
                  </CardFooter>
                </Card>
              </motion.div>
            ))}
          </AnimatePresence>
        </div>
      )}
    </div>
  );
};
