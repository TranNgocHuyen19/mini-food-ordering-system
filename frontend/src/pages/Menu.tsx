import { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { ShoppingCart, Search } from 'lucide-react';
import { foodService } from '../services';
import type { Food } from '../types';
import { useCart } from '../context/CartContext';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Badge } from '@/components/ui/badge';
import { Card, CardHeader, CardTitle, CardDescription, CardContent, CardFooter } from '@/components/ui/card';
import { Skeleton } from '@/components/ui/skeleton';
import { toast } from 'sonner';

export const Menu = () => {
  const [foods, setFoods] = useState<Food[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [availableCategories, setAvailableCategories] = useState<string[]>(['All']);
  const { addToCart } = useCart();

  const fetchFoods = async (category: string) => {
    setLoading(true);
    try {
      const response = category === 'All' 
        ? await foodService.getAllFoods()
        : await foodService.getFoodsByCategory(category);

      if (response.success && response.data) {
        setFoods(response.data);
        
        // Update available categories ONLY if we're on 'All' to get the full list
        if (category === 'All') {
          const cats = ['All', ...new Set(response.data.map(f => f.category).filter(Boolean) as string[])];
          setAvailableCategories(cats);
        }
      } else {
        throw new Error(response.message || 'Unknown error');
      }
    } catch (error: any) {
      toast.error('Failed to load menu items: ' + (error.message || 'Please try again later.'));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchFoods(selectedCategory);
  }, [selectedCategory]);

  const filteredFoods = foods.filter((food) => {
    return food.name.toLowerCase().includes(searchTerm.toLowerCase());
  });

  return (
    <div className="space-y-12 pb-20">
      <header className="relative py-24 px-8 overflow-hidden rounded-3xl bg-slate-900 text-white">
        <div className="absolute inset-0 bg-gradient-to-br from-primary/40 to-transparent z-10" />
        <div 
          className="absolute inset-0 opacity-40 mix-blend-overlay z-0" 
          style={{ backgroundImage: 'url("https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=2070")' }} 
        />
        <div className="relative z-20 max-w-2xl">
          <motion.h1 
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            className="text-5xl md:text-7xl font-black tracking-tight mb-6 bg-gradient-to-r from-white to-white/70 bg-clip-text text-transparent"
          >
            Delicious Food <br /> Delivered to You
          </motion.h1>
          <motion.p 
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ delay: 0.1 }}
            className="text-xl text-slate-300 leading-relaxed mb-8"
          >
            Choose from our diverse menu and enjoy a premium dining experience at home.
          </motion.p>
        </div>
      </header>

      <div className="sticky top-20 z-40 bg-background/80 backdrop-blur-md p-4 rounded-xl shadow-lg border border-border/40 flex flex-col md:flex-row gap-4 items-center justify-between">
        <div className="relative w-full md:w-96 group">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-muted-foreground group-focus-within:text-primary transition-colors" size={18} />
          <Input
            placeholder="Search our delicious menu..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="pl-10 h-11 bg-muted/30 border-none group-focus-within:bg-background transition-all"
          />
        </div>
        <div className="flex gap-2 w-full md:w-auto overflow-x-auto pb-1 md:pb-0 scrollbar-hide">
          {availableCategories.map((category) => (
            <Button
              key={category}
              variant={selectedCategory === category ? 'default' : 'ghost'}
              onClick={() => setSelectedCategory(category)}
              className="rounded-full whitespace-nowrap h-10 px-6 font-medium tracking-wide transition-all"
            >
              {category}
            </Button>
          ))}
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
        {loading ? (
          Array.from({ length: 8 }).map((_, i) => (
            <div key={i} className="space-y-4">
              <Skeleton className="h-48 w-full rounded-2xl" />
              <div className="space-y-2">
                <Skeleton className="h-6 w-2/3" />
                <Skeleton className="h-4 w-full" />
              </div>
            </div>
          ))
        ) : (
          <AnimatePresence mode="popLayout">
            {filteredFoods.map((food, index) => (
              <motion.div
                key={food.id}
                layout
                initial={{ opacity: 0, y: 30 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, scale: 0.9 }}
                transition={{ duration: 0.4, delay: index * 0.05 }}
              >
                <Card className="group border-none bg-card hover:bg-accent/40 transition-all duration-500 overflow-hidden shadow-sm hover:shadow-2xl rounded-2xl h-full flex flex-col">
                  <div className="relative h-56 overflow-hidden">
                    <img
                      src={food.imageUrl || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c'}
                      alt={food.name}
                      className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
                    />
                    <div className="absolute top-4 left-4 z-20">
                      <Badge className="bg-background/80 backdrop-blur-md text-foreground border-none px-3 py-1 text-xs font-semibold rounded-full shadow-sm">
                        {food.category || 'General'}
                      </Badge>
                    </div>
                    {food.quantity <= 0 && (
                      <div className="absolute inset-0 bg-black/60 backdrop-blur-[2px] flex items-center justify-center z-30">
                        <Badge variant="destructive" className="text-lg font-black px-6 py-2 rounded-xl shadow-2xl -rotate-12 border-4 border-white/20">
                          HẾT HÀNG
                        </Badge>
                      </div>
                    )}
                  </div>
                  <CardHeader className="pt-6">
                    <CardTitle className="text-xl font-bold tracking-tight">{food.name}</CardTitle>
                    <CardDescription className="line-clamp-2 text-sm leading-relaxed">{food.description}</CardDescription>
                  </CardHeader>
                  <CardContent className="flex-grow space-y-4">
                    <div className="flex items-center justify-between">
                      <span className="text-2xl font-black text-primary">${food.price.toFixed(2)}</span>
                      <Badge variant={food.quantity > 0 ? "secondary" : "destructive"} className="rounded-full">
                        {food.quantity > 0 ? `Stock: ${food.quantity}` : 'Out of Stock'}
                      </Badge>
                    </div>
                  </CardContent>
                  <CardFooter className="pt-2 pb-6 px-6">
                    <Button 
                      className="w-full rounded-xl h-11 font-bold tracking-wide shadow-lg shadow-primary/20 hover:shadow-primary/40 active:scale-95 transition-all"
                      disabled={food.quantity <= 0}
                      onClick={() => {
                        addToCart(food);
                        toast.success(`${food.name} added to cart`);
                      }}
                    >
                      <ShoppingCart className="mr-2" size={18} />
                      {food.quantity > 0 ? 'Add to Cart' : 'Unavailable'}
                    </Button>
                  </CardFooter>
                </Card>
              </motion.div>
            ))}
          </AnimatePresence>
        )}
      </div>
    </div>
  );
};
